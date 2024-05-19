package com.workintech.ecommercebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workintech.ecommercebackend.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

}
