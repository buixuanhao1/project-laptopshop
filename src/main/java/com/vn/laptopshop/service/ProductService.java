package com.vn.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartService cartService;
    private final CartDetailService cartDetailService;

    public ProductService(ProductRepository productRepository, UserService userService, CartService cartService,
            CartDetailService cartDetailService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
    }

    public Product SaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> FindAllProducts() {
        return this.productRepository.findAll();
    }

    public Optional<Product> FindProductById(Long id) {
        return this.productRepository.findById(id);
    }

    public void DeleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(Long id, String email, HttpSession session) {
        User user = this.userService.FindUserByEmail(email);
        if (user.getCart() == null) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setSum(0);
            cart = this.cartService.HandleSaveCart(cart);
            user.setCart(cart);
        }

        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            CartDetail oldCartDetail = this.cartDetailService.FindCartDetailByCartAndProduct(user.getCart(), product);
            if (oldCartDetail == null) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setProduct(product);
                cartDetail.setCart(user.getCart());
                cartDetail.setPrice(product.getPrice());
                cartDetail.setQuantity(1);
                this.cartDetailService.HandleSaveCartDetail(cartDetail);
            } else {
                oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
            }
        }
        session.setAttribute("sum", this.cartDetailService.CountCartDetailsByCartId(user.getCart().getId()));

    }
}
