package com.boki.cafekiosk.spring.domain.order;

import com.boki.cafekiosk.spring.domain.BaseEntity;
import com.boki.cafekiosk.spring.domain.orderproduct.OrderProduct;
import com.boki.cafekiosk.spring.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders") // 예약어를 피하기 위함
@Entity
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registerDt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 연관관계 주인
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Order(List<Product> products, OrderStatus orderStatus, LocalDateTime registerDt) {
        this.orderStatus = orderStatus;
        this.totalPrice = calculateTotalPrice(products);
        this.registerDt = registerDt;
        this.orderProducts = products.stream()
                .map(product -> new OrderProduct(this, product))
                .collect(Collectors.toList());
    }

    public static Order create(List<Product> products, LocalDateTime registerDt) {
        return Order.builder()
                .orderStatus(OrderStatus.INIT)
                .products(products)
                .registerDt(registerDt)
                .build();
    }

    private int calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
