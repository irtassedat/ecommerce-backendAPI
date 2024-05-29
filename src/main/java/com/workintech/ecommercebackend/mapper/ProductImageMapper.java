package com.workintech.ecommercebackend.mapper;

import com.workintech.ecommercebackend.dto.ProductImageDto;
import com.workintech.ecommercebackend.entity.ProductImage;
import org.mapstruct.Mapper;

@Mapper
public interface ProductImageMapper {
    ProductImageDto toDto(ProductImage productImage);
    ProductImage toEntity(ProductImageDto productImageDto);
}