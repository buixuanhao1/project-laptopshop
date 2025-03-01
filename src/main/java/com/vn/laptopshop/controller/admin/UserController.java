package com.vn.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vn.laptopshop.domain.Role;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.service.RoleService;
import com.vn.laptopshop.service.UploadFileService;
import com.vn.laptopshop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder,
            UploadFileService uploadFileService, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.uploadFileService = uploadFileService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/user/create")
    public String createUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUser(@ModelAttribute("newUser") @Valid User newUser,
            BindingResult bindingResult,
            @RequestParam("hao_File") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            return "admin/user/create";
        }
        Optional<Role> role = this.roleService.findRoleByName(newUser.getRole().getName());
        if (role.isPresent()) {
            newUser.setRole(role.get());
        }
        String avatar = this.uploadFileService.handleSaveUpLoadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(newUser.getPassWord());
        newUser.setPassWord(hashPassword);
        newUser.setAvatar(avatar);
        this.userService.SaveUser(newUser);

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user")
    public String userPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        Pageable pageable = PageRequest.of(page - 1, 2);
        model.addAttribute("users", this.userService.FindAllUser(pageable).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", this.userService.FindAllUser(pageable).getTotalPages());
        return "admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getViewPage(@PathVariable Long id, Model model) {
        Optional<User> user = this.userService.FindUserById(id);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdatePage(@PathVariable Long id, Model model) {
        Optional<User> user = this.userService.FindUserById(id);
        if (user.isPresent()) {
            model.addAttribute("newUser", user.get());
        }
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String UpdateUser(@ModelAttribute("newUser") @Valid User newUser,
            BindingResult bindingResult,
            @RequestParam("hao_File") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            return "admin/user/update";
        }
        Optional<Role> role = this.roleService.findRoleByName(newUser.getRole().getName());
        if (role.isPresent()) {
            newUser.setRole(role.get());
        }
        String avatar = this.uploadFileService.handleSaveUpLoadFile(file, "avatar");
        newUser.setAvatar(avatar);
        this.userService.SaveUser(newUser);

        // Optional<User> currentUser = userService.FindUserById(hao.getId());
        // String avatar = this.uploadFileService.handleSaveUpLoadFile(file, "avatar");
        // Optional<Role> role =
        // this.roleService.findRoleByName(hao.getRole().getName());
        // if (currentUser.isPresent()) {
        // currentUser.get().setAddress(hao.getAddress());
        // currentUser.get().setEmail(hao.getEmail());
        // currentUser.get().setPhone(hao.getPhone());
        // currentUser.get().setAvatar(avatar);
        // currentUser.get().setRole(role.get());

        // }
        // this.userService.SaveUser(currentUser.get());
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeletePage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        this.userService.DeleteUserById(id);
        return "redirect:/admin/user";
    }

}
