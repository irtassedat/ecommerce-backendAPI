package com.workintech.ecommercebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "shopping_carts")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("shoppingCart")
    private User user;

    @OneToMany(mappedBy = "shoppingCart")
    @JsonIgnoreProperties("shoppingCart")
    private Set<Product> products;
}
