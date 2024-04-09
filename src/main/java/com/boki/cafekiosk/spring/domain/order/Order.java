package com.boki.cafekiosk.spring.domain.order;

import com.boki.cafekiosk.spring.domain.BaseEntity;
import com.boki.cafekiosk.spring.domain.orderproduct.OrderProduct;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

}
