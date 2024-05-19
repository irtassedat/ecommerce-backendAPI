package com.workintech.ecommercebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workintech.ecommercebackend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

