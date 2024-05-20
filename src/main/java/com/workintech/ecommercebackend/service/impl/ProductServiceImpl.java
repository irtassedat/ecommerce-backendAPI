package com.workintech.ecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.workintech.ecommercebackend.dto.CategoryDto;
import com.workintech.ecommercebackend.dto.ProductDto;
import com.workintech.ecommercebackend.dto.ProductImageDto;
import com.workintech.ecommercebackend.dto.StoreDto;
import com.workintech.ecommercebackend.entity.Category;
import com.workintech.ecommercebackend.entity.ProductImage;
import com.workintech.ecommercebackend.entity.Store;
import com.workintech.ecommercebackend.exception.ProductServiceException;
import com.workintech.ecommercebackend.exception.ResourceNotFoundException;
import com.workintech.ecommercebackend.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.workintech.ecommercebackend.entity.Product;
import com.workintech.ecommercebackend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductServiceException("No products found.");
        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        logger.info("Fetching product with id: {}", id);
        return productRepository.findById(id).map(this::convertToDto);
    }


    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Creating new product: {}", productDto.getName());
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        logger.info("Updating product with id: {}", id);
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
        updateEntityFromDto(existingProduct, productDto);
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
        productRepository.delete(existingProduct);
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
    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setRating(productDto.getRating());
        product.setSellCount(productDto.getSellCount());
        product.setImg(productDto.getImg());

        if (productDto.getStore() != null) {
            Store store = new Store();
            store.setId(productDto.getStore().getId());
            store.setName(productDto.getStore().getName());
            product.setStore(store);
        }

        Set<Category> categories = productDto.getCategories().stream()
                .map(categoryDto -> {
                    Category category = new Category();
                    category.setId(categoryDto.getId());
                    category.setCode(categoryDto.getCode());
                    return category;
                }).collect(Collectors.toSet());
        product.setCategories(categories);

        Set<ProductImage> images = productDto.getImages().stream()
                .map(imageDto -> {
                    ProductImage image = new ProductImage();
                    image.setId(imageDto.getId());
                    image.setUrl(imageDto.getUrl());
                    image.setIndex(imageDto.getIndex());
                    return image;
                }).collect(Collectors.toSet());
        product.setImages(images);

        return product;
    }
    private void updateEntityFromDto(Product existingProduct, ProductDto productDto) {
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStock(productDto.getStock());
        existingProduct.setRating(productDto.getRating());
        existingProduct.setSellCount(productDto.getSellCount());

        Set<Category> categories = productDto.getCategories().stream()
                .map(categoryDto -> {
                    Category category = new Category();
                    category.setId(categoryDto.getId());
                    category.setCode(categoryDto.getCode());
                    return category;
                }).collect(Collectors.toSet());
        existingProduct.setCategories(categories);

        if (productDto.getStore() != null) {
            Store store = new Store();
            store.setId(productDto.getStore().getId());
            store.setName(productDto.getStore().getName());
            existingProduct.setStore(store);
        }

        Set<ProductImage> images = productDto.getImages().stream()
                .map(imageDto -> {
                    ProductImage image = new ProductImage();
                    image.setId(imageDto.getId());
                    image.setUrl(imageDto.getUrl());
                    image.setIndex(imageDto.getIndex());
                    return image;
                }).collect(Collectors.toSet());
        existingProduct.setImages(images);
    }

}

