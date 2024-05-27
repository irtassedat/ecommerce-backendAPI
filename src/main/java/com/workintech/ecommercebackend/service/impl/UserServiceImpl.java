package com.workintech.ecommercebackend.service.impl;

import com.workintech.ecommercebackend.dto.LoginDto;
import com.workintech.ecommercebackend.entity.Role;
import com.workintech.ecommercebackend.entity.User;
import com.workintech.ecommercebackend.repository.RoleRepository;
import com.workintech.ecommercebackend.repository.UserRepository;
import com.workintech.ecommercebackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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
        // Şifreyi encode ederek kaydedelim
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Kullanıcıya varsayılan rol atayalım
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);
        } else {
            Set<Role> roles = new HashSet<>();
            for (Role role : user.getRoles()) {
                Role existingRole = roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(existingRole);
            }
            user.setRoles(roles);
        }

        // Name alanı boşsa hata fırlat
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        return userRepository.save(user);
    }


    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
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
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
