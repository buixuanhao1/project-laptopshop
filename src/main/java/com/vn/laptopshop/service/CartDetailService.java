package com.vn.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.repository.CartDetailsRepository;

@Service
public class CartDetailService {
    private final CartDetailsRepository cartDetailsRepository;

    public CartDetailService(CartDetailsRepository cartDetailsRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
    }

    public CartDetail HandleSaveCartDetail(CartDetail cartDetail) {
        return this.cartDetailsRepository.save(cartDetail);
    }

    public Optional<CartDetail> FindCartDetailsById(Long id) {
        return this.cartDetailsRepository.findById(id);
    }

    public CartDetail FindCartDetailByCartAndProduct(Cart cart, Product product) {
        return this.cartDetailsRepository.findByCartAndProduct(cart, product);
    }

    public int CountCartDetailsByCartId(Long id) {
        return this.cartDetailsRepository.countCartDetailsByCartId(id);
    }

    public List<CartDetail> FindAllCartDetailsByCart(Cart cart) {
        return this.cartDetailsRepository.findAllByCart(cart);
    }
}
