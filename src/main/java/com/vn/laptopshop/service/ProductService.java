package com.vn.laptopshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Cart;
import com.vn.laptopshop.domain.CartDetail;
import com.vn.laptopshop.domain.Order;
import com.vn.laptopshop.domain.OrderDetail;
import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.repository.OrderRepository;
import com.vn.laptopshop.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartService cartService;
    private final CartDetailService cartDetailService;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, UserService userService, CartService cartService,
            CartDetailService cartDetailService, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
        this.orderRepository = orderRepository;
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
            session.setAttribute("cart_id", cart.getId());
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
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailService.FindCartDetailsById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailService.HandleSaveCartDetail(currentCartDetail);
            }
        }
    }

    @Transactional
    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        if (user != null) {
            Order order = new Order();
            order.setReceiverAddress(receiverAddress);
            order.setReceiverName(receiverName);
            order.setReceiverPhone(receiverPhone);
            order.setStatus("Pending");
            order.setUser(user);
            order.setTotalPrice(this.cartDetailService.SumPriceByCart(user.getCart().getId()));

            this.orderRepository.save(order); // Save order done

            // Lấy Cart từ database thay vì user.getCart()
            Cart cart = this.cartService.FindCartByUser(user);
            List<CartDetail> cartDetails = this.cartDetailService.FindAllCartDetailsByCart(cart);

            List<OrderDetail> orderDetails = new ArrayList<>();
            for (CartDetail cartDetail : cartDetails) {
                OrderDetail od = new OrderDetail();
                od.setOrder(order);
                od.setPrice(cartDetail.getPrice());
                od.setProduct(cartDetail.getProduct());
                od.setQuantity(cartDetail.getQuantity());
                orderDetails.add(od);
            }

            user.setCart(null);
            this.userService.SaveUser(user);
            this.cartService.DeleteCartByUser(user); // delete both
            order.setOrderDetails(orderDetails); // Save both
            session.setAttribute("cart_id", null);
        }
    }

}
