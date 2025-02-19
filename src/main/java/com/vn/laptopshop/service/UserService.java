package com.vn.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.domain.DTO.RegisterDTO;
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

    public void DeleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public User TransferRegisterDtoToUser(RegisterDTO registerDTO) {
        if (registerDTO != null) {
            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setName(registerDTO.getLastName() + " " + registerDTO.getFirstName());
            user.setPassWord(registerDTO.getPassword());
            return user;
        }
        return null;
    }

    public boolean CheckEmailExists(String email) {
        if (this.userRepository.existsByEmail(email))
            return true;
        return false;
    }

}
