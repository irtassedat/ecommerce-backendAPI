package com.workintech.ecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
