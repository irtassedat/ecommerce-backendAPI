package com.workintech.ecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.workintech.ecommercebackend.dto.ProductDto;
import com.workintech.ecommercebackend.entity.Category;
import com.workintech.ecommercebackend.entity.Product;
import com.workintech.ecommercebackend.exception.ProductServiceException;
import com.workintech.ecommercebackend.exception.ResourceNotFoundException;
import com.workintech.ecommercebackend.mapper.ProductMapper;
import com.workintech.ecommercebackend.repository.ProductCategoryRepository;
import com.workintech.ecommercebackend.repository.ProductImageRepository;
import com.workintech.ecommercebackend.repository.ProductRepository;
import com.workintech.ecommercebackend.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductServiceException("No products found.");
        }
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        logger.info("Fetching product with id: {}", id);
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            // Manually initialize collections
            Hibernate.initialize(product.getCategories());
            Hibernate.initialize(product.getImages());
            return Optional.of(productMapper.toDto(product));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Creating new product: {}", productDto.getName());
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        logger.info("Updating product with id: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
        productMapper.updateEntityFromDto(productDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));

        // Remove associations in product_category table
        productCategoryRepository.deleteByProductId(id);
        productImageRepository.deleteByProductId(id);

        productRepository.delete(existingProduct);
        productRepository.flush(); // Değişiklikleri hemen veri tabanına yansıtmak için flush kullanımı
        if (productRepository.existsById(id)) {
            throw new RuntimeException("Product was not deleted");
        }
    }
}
