package com.vn.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetail, Long> {
    CartDetail save(CartDetail cartDetail);

    Optional<CartDetail> findById(long id);

    CartDetail findByCartAndProduct(Cart cart, Product product);

    @Query("SELECT COUNT(cd) FROM Cart c JOIN c.cartDetails cd WHERE c.id = :id")
    int countCartDetailsByCartId(@Param("id") Long id);

    List<CartDetail> findAllByCart(Cart cart);
}
