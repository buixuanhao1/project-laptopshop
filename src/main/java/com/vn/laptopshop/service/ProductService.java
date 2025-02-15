package com.vn.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
}
