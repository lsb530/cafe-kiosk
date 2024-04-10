package com.boki.cafekiosk.spring.api.service.order;

import com.boki.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.boki.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.boki.cafekiosk.spring.domain.order.Order;
import com.boki.cafekiosk.spring.domain.order.OrderRepository;
import com.boki.cafekiosk.spring.domain.product.Product;
import com.boki.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registerDt) {
        List<String> productNumbers = request.getProductNumbers();

        // Product
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        // Order
        Order order = Order.create(products, registerDt);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }
}
