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
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registerDt) {
        List<String> productNumbers = request.getProductNumbers();

        // Product
        List<Product> duplicateProducts = findProductsBy(productNumbers);

        // Order
        Order order = Order.create(duplicateProducts, registerDt);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}
