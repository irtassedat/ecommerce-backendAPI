package com.workintech.ecommercebackend.controller;

import java.util.List;
import java.util.Optional;

import com.workintech.ecommercebackend.dto.ProductDto;
import com.workintech.ecommercebackend.exception.ResourceNotFoundException;
import com.workintech.ecommercebackend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.workintech.ecommercebackend.service.ProductService;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<ProductDto> productDto = productService.getProductById(id);
        return productDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        if (productRepository.existsById(id)) {
            throw new RuntimeException("Product was not deleted");
        }
        return ResponseEntity.ok("Product successfully deleted");
    }

}


