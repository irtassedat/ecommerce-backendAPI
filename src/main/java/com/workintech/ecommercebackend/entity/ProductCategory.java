package com.workintech.ecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("productCategories")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("productCategories")
    private Category category;
}
