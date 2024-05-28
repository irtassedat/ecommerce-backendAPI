package com.workintech.ecommercebackend.repository;

import com.workintech.ecommercebackend.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    void deleteByProductId(Long productId);
}
