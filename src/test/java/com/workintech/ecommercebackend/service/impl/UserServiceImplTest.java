package com.workintech.ecommercebackend.service.impl;

import com.workintech.ecommercebackend.entity.User;
import com.workintech.ecommercebackend.repository.UserRepository;
import com.workintech.ecommercebackend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest  {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    private User user;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(2L);
        user.setEmail("jane.smith@example.com");
        user.setPassword("123456");
        user.setName("Jane Smith");
        // Set other fields...
    }

    @Test
    public void testSaveUser() {
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.createUser(user);
        assertEquals(user, savedUser);
        assertEquals("Jane Smith", savedUser.getName());
        assertEquals("jane.smith@example.com", savedUser.getEmail());
        assertTrue(passwordEncoder.matches("123456", savedUser.getPassword()));
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        assertEquals(List.of(user), userService.getAllUsers());
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User foundUser = userService.getUserById(1L);
        assertNull(foundUser);
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setId(2L);
        updatedUser.setEmail("janee.smith@example.com");
        updatedUser.setPassword("1234567");
        updatedUser.setName("Janev2 Smith");


        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        User savedUser = userService.updateUser(2L, updatedUser);
        assertEquals(updatedUser, savedUser);
        assertEquals("Janev2 Smith", savedUser.getName());
}
}
