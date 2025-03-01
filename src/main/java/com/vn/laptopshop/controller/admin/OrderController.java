package com.vn.laptopshop.controller.admin;

import java.util.Optional;

import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vn.laptopshop.domain.Order;
import com.vn.laptopshop.repository.OrderDetailsRepository;
import com.vn.laptopshop.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderController(OrderService orderService, OrderDetailsRepository orderDetailsRepository) {
        this.orderService = orderService;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @GetMapping("/admin/order")
    public String GetOrderPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        Pageable pageable = PageRequest.of(page - 1, 2);

        model.addAttribute("orders", this.orderService.FetchAllOrders(pageable).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", this.orderService.FetchAllOrders(pageable).getTotalPages());
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String GetViewOrder(@PathVariable long id, Model model) {
        Order order = this.orderService.FindOrderById(id);
        model.addAttribute("orderDetails", this.orderDetailsRepository.findAllByOrder(order));
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String GetViewUpdate(@PathVariable long id, Model model) {
        Order order = this.orderService.FindOrderById(id);
        model.addAttribute("newOrder", order);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String UpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.SaveOrder(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeletePage(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String DeleteOrder(@RequestParam("id") long id) {
        this.orderService.deleteOrderById(id);
        return "redirect:/admin/order";
    }

}
