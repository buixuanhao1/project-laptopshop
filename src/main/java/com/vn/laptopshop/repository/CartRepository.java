package com.vn.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart save(Cart cart);

    Optional<Cart> findById(long id);

    Cart findByUser(User user);

    void deleteByUser(User user);

    void deleteById(Long id);

}
