// ProductMapper.java
package com.workintech.ecommercebackend.mapper;

import com.workintech.ecommercebackend.dto.ProductDto;
import com.workintech.ecommercebackend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CategoryMapper.class, ProductImageMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);
}
