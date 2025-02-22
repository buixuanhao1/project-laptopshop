package com.vn.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vn.laptopshop.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart save(Cart cart);

}
