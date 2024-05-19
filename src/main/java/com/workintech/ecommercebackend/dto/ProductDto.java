package com.workintech.ecommercebackend.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private double rating;
    private int sellCount;
    private String img;
    private StoreDto store;
    private Set<CategoryDto> categories;
    private Set<ProductImageDto> images;
}
