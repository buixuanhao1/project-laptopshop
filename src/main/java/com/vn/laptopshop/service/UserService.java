package com.vn.laptopshop.service;

import java.util.List;
import java.util.Optional;

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

    public List<User> FindAllUser() {
        return this.userRepository.findAll();
    }

    public Optional<User> FindUserById(Long id) {
        return this.userRepository.findById(id);
    }
}
