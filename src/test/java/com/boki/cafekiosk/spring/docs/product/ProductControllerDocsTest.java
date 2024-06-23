package com.boki.cafekiosk.spring.docs.product;

import com.boki.cafekiosk.spring.api.controller.product.ProductController;
import com.boki.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import com.boki.cafekiosk.spring.api.service.product.ProductResponse;
import com.boki.cafekiosk.spring.api.service.product.ProductService;
import com.boki.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import com.boki.cafekiosk.spring.docs.RestDocsSupport;
import com.boki.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.boki.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = Mockito.mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @DisplayName("신규 상품을 등록하는 API")
    @Test
    void createProduct() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingStatus.SELLING)
            .name("아메리카노")
            .price(4000)
            .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(1L)
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build()
            );

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("product-create", // id값
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("type").type(JsonFieldType.STRING).description("상품 타입"),
                    fieldWithPath("sellingStatus").type(JsonFieldType.STRING).optional().description("상품 판매 상태"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격")
                ), // 요청필드
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("상품 ID"),
                    fieldWithPath("data.productNumber").type(JsonFieldType.STRING).description("상품 번호"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING).description("상품 타입"),
                    fieldWithPath("data.sellingStatus").type(JsonFieldType.STRING).description("상품 판매 상태"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("상품 이름"),
                    fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("상품 가격")
                ) // 응답필드
            ));
    }
}
