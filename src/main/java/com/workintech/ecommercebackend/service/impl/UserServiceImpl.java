package com.workintech.ecommercebackend.service.impl;

import com.workintech.ecommercebackend.dto.LoginDto;
import com.workintech.ecommercebackend.entity.Role;
import com.workintech.ecommercebackend.entity.User;
import com.workintech.ecommercebackend.repository.RoleRepository;
import com.workintech.ecommercebackend.repository.UserRepository;
import com.workintech.ecommercebackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Role existingRole = roleRepository.findById(role.getId()).orElseThrow(() -> new RuntimeException("Role not found"));
            role.setName(existingRole.getName());

        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User loginUser(LoginDto loginDto) {
        return userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));  }
}