package com.boki.cafekiosk.spring.api.controller.product.dto.request;

import com.boki.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import com.boki.cafekiosk.spring.domain.product.Product;
import com.boki.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.boki.cafekiosk.spring.domain.product.ProductType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    // String name -> 상품 이름은 20자 제한
    @NotBlank(message = "상품 이름은 필수입니다.")
    // @NotNull // "", "  " -> OK
    // @NotEmpty // "" -> OK
    // @Max(20) // 도메인에 관련된 건 안쪽 레이어에서 검증하자
    private String name;

    @Positive(message = "상품 가격은 양수여야 합니다.")
    private int price;

    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
