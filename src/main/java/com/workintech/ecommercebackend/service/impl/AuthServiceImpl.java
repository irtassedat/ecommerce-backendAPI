package com.workintech.ecommercebackend.service;

import com.workintech.ecommercebackend.entity.User;
import com.workintech.ecommercebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        // Şifreyi şifrele
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Kullanıcıyı veritabanına kaydet
        return userRepository.save(user);
    }
}