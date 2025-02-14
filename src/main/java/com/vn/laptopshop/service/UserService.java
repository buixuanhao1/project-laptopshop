package com.vn.laptopshop.service;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.repository.UserRepository;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User SaveUser(User user) {
        return this.userRepository.save(user);
    }
}
