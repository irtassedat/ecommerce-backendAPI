package com.workintech.ecommercebackend.repository;

import com.workintech.ecommercebackend.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Modifying
    @Query("DELETE FROM ProductCategory pc WHERE pc.product.id = :productId")
    void deleteByProductId(Long productId);

    List<ProductCategory> findProductCategoriesByProduct_Id(Long id);
}
