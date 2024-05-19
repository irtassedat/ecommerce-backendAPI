package com.workintech.ecommercebackend.repository;

import com.workintech.ecommercebackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workintech.ecommercebackend.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r JOIN User u WHERE u.id = :userId")
    Set<Role> findByUserId(@Param("userId") Long userId);
}
