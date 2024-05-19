package com.workintech.ecommercebackend.service;

import java.util.List;

import com.workintech.ecommercebackend.dto.ProductDto;
import com.workintech.ecommercebackend.entity.Product;

public interface ProductService {
    List<ProductDto> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}

