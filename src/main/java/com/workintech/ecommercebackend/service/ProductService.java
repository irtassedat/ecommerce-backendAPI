package com.workintech.ecommercebackend.service;

import java.util.List;
import java.util.Optional;

import com.workintech.ecommercebackend.dto.ProductDto;
import com.workintech.ecommercebackend.entity.Product;

public interface ProductService {
    List<ProductDto> getAllProducts();
    Optional<ProductDto> getProductById(Long id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}

