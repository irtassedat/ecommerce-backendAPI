package com.workintech.ecommercebackend.repository;

import com.workintech.ecommercebackend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}