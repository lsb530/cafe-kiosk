package com.boki.cafekiosk.spring.api.service.order.response;

import com.boki.cafekiosk.spring.api.service.product.ProductResponse;
import com.boki.cafekiosk.spring.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private LocalDateTime registerDt;
    private List<ProductResponse> products;

    @Builder
    public OrderResponse(Long id, int totalPrice, LocalDateTime registerDt, List<ProductResponse> products) {
        this.id = id;
        this.registerDt = registerDt;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registerDt(order.getRegisterDt())
                .products(order.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
