package com.workintech.ecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String name;
    private String surname;
    private String phone;
    private String city;
    private String district;
    private String neighborhood;
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("addresses")
    private User user;
}
