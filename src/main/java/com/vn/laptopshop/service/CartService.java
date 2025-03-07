package com.vn.laptopshop.service;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.User;
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

    public Cart FindCartById(long id) {
        return this.cartRepository.findById(id).get();
    }

    public void DeleteCartByUser(User user) {
        this.cartRepository.deleteByUser(user);
    }

    public Cart FindCartByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

}
