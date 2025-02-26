package com.vn.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vn.laptopshop.domain.OrderDetail;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

}
