package com.boki.cafekiosk.spring.api.controller.product;

import com.boki.cafekiosk.spring.api.ApiResponse;
import com.boki.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import com.boki.cafekiosk.spring.api.service.product.ProductResponse;
import com.boki.cafekiosk.spring.api.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
    }

    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        return ApiResponse.ok(productService.getSellingProducts());
    }
}
