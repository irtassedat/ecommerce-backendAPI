package com.workintech.ecommercebackend.mapper;

import com.workintech.ecommercebackend.dto.CategoryDto;
import com.workintech.ecommercebackend.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}