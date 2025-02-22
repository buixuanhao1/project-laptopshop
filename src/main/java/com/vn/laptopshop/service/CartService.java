package com.vn.laptopshop.service;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart HandleSaveCart(Cart cart) {
        return this.cartRepository.save(cart);
    }

}
