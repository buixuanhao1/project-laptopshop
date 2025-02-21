package com.vn.laptopshop.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.domain.DTO.RegisterDTO;
import com.vn.laptopshop.service.ProductService;
import com.vn.laptopshop.service.RoleService;
import com.vn.laptopshop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public HomePageController(ProductService productService,
            UserService userService,
            PasswordEncoder passwordEncoder, RoleService roleService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("products", this.productService.FindAllProducts());
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String RegisterNewUser(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String RegisterNewUser(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.TransferRegisterDtoToUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());
        user.setPassWord(hashPassword);
        user.setRole(this.roleService.findRoleByName("USER").get());
        this.userService.SaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String DenyPage() {
        return "client/auth/deny";
    }

    @GetMapping("/product/{id}")
    public String DetailsProductsPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", this.productService.FindProductById(id).get());
        return "client/product/detail";
    }

}
