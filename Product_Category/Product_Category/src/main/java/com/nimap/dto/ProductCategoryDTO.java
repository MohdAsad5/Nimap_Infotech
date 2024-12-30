package com.nimap.dto;

import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productColor;
    private String productBrand;
    private String productDetails;
    private Long categoryId;
    private String categoryName;

    public ProductCategoryDTO(Long productId, String productName, Double productPrice, String productColor,
                              String productBrand, String productDetails, Long categoryId, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productColor = productColor;
        this.productBrand = productBrand;
        this.productDetails = productDetails;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}

