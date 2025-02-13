package com.vn.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vn.laptopshop.domain.User;

@Controller
public class UserController {

    @GetMapping("/admin/user")
    public String getHomePage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
}
