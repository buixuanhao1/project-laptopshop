package com.vn.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vn.laptopshop.service.OrderService;
import com.vn.laptopshop.service.ProductService;
import com.vn.laptopshop.service.UserService;

@Controller
public class Dashboard {
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    public Dashboard(UserService userService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        model.addAttribute("countUsers", this.userService.CountUses());
        model.addAttribute("countOrders", this.orderService.CountOrders());
        model.addAttribute("countProducts", this.productService.CountProducts());
        return "admin/dashboard/show";
    }

}
