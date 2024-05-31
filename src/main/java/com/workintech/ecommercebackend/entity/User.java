package com.workintech.ecommercebackend.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties("users")
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Card> cards;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private ShoppingCart shoppingCart;
}