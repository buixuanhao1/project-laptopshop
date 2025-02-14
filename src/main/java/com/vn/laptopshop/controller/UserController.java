package com.vn.laptopshop.controller;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.service.UploadFileService;
import com.vn.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder,
            UploadFileService uploadFileService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/admin/user/create")
    public String getHomePage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(@ModelAttribute("newUser") User newUser,
            @RequestParam("hao_File") MultipartFile file) {

        String avatar = this.uploadFileService.handleSaveUpLoadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(newUser.getPassWord());
        newUser.setPassWord(hashPassword);
        newUser.setAvatar(avatar);
        this.userService.SaveUser(newUser);

        return "admin/user/create";
    }
}
