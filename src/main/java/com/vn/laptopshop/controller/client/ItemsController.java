package com.vn.laptopshop.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.service.CartDetailService;
import com.vn.laptopshop.service.CartService;
import com.vn.laptopshop.service.ProductService;
import com.vn.laptopshop.service.RoleService;
import com.vn.laptopshop.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemsController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final CartDetailService cartDetailService;
    private final CartService cartService;

    public ItemsController(ProductService productService,
            UserService userService,
            PasswordEncoder passwordEncoder, RoleService roleService,
            CartDetailService cartDetailService,
            CartService cartService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.cartDetailService = cartDetailService;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String CartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("id");
        long cart_id = (long) session.getAttribute("cart_id");
        Cart cart = this.cartService.FindCartById(cart_id);
        session.setAttribute("sum", cart == null ? 0 : this.cartDetailService.CountCartDetailsByCartId(cart.getId()));
        Optional<User> OtpUser = this.userService.FindUserById(id);
        List<CartDetail> cartDetails = this.cartDetailService.FindAllCartDetailsByCart(OtpUser.get().getCart());
        if (OtpUser.isPresent()) {
            model.addAttribute("cartDetails", cartDetails);
        }
        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String DeleteCartDetail(@PathVariable Long id) {
        this.cartDetailService.DeleteCartDetailById(id);
        return "redirect:/cart";
    }
}
