package com.workintech.ecommercebackend.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.workintech.ecommercebackend.dto.CategoryDto;
import com.workintech.ecommercebackend.dto.ProductDto;
import com.workintech.ecommercebackend.dto.ProductImageDto;
import com.workintech.ecommercebackend.dto.StoreDto;
import com.workintech.ecommercebackend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.workintech.ecommercebackend.entity.Product;
import com.workintech.ecommercebackend.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            existingProduct.setRating(product.getRating());
            existingProduct.setSellCount(product.getSellCount());
            existingProduct.setCategories(product.getCategories());
            existingProduct.setStore(product.getStore());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setRating(product.getRating());
        productDto.setSellCount(product.getSellCount());
        productDto.setImg(product.getImg());

        if (product.getStore() != null) {
            StoreDto storeDto = new StoreDto();
            storeDto.setId(product.getStore().getId());
            storeDto.setName(product.getStore().getName());
            productDto.setStore(storeDto);
        }

        Set<CategoryDto> categoryDtos = product.getCategories().stream()
                .map(category -> {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(category.getId());
                    categoryDto.setCode(category.getCode());
                    return categoryDto;
                }).collect(Collectors.toSet());
        productDto.setCategories(categoryDtos);

        Set<ProductImageDto> imageDtos = product.getImages().stream()
                .map(image -> {
                    ProductImageDto imageDto = new ProductImageDto();
                    imageDto.setId(image.getId());
                    imageDto.setUrl(image.getUrl());
                    imageDto.setIndex(image.getIndex());
                    return imageDto;
                }).collect(Collectors.toSet());
        productDto.setImages(imageDtos);

        return productDto;
    }
}

