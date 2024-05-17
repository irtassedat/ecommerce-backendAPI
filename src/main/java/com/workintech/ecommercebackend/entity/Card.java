package com.workintech.ecommercebackend.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    private String cardHolderName;

    private String expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

