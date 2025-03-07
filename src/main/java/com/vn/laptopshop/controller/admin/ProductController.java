package com.vn.laptopshop.controller.admin;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vn.laptopshop.domain.Product;
import com.vn.laptopshop.service.ProductService;
import com.vn.laptopshop.service.UploadFileService;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadFileService uploadFileService;

    public ProductController(ProductService productService, UploadFileService uploadFileService) {
        this.productService = productService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/admin/product")
    public String getHomePage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception exception) {

        }

        Pageable pageable = PageRequest.of(page - 1, 2);
        model.addAttribute("products", this.productService.FindAllProducts(pageable).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", this.productService.FindAllProducts(pageable).getTotalPages());

        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String createProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(@ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult bindingResult,
            @RequestParam("hao_File") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }
        String avatar = this.uploadFileService.handleSaveUpLoadFile(file, "product");
        newProduct.setImage(avatar);
        this.productService.SaveProduct(newProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getViewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", this.productService.FindProductById(id).get());
        return "admin/product/details";
    }

    @GetMapping("/admin/product/update/{id}")
    public String UpdateProductPage(@PathVariable Long id, Model model) {
        Optional<Product> product = this.productService.FindProductById(id);
        if (!product.isPresent()) {
            return "redirect:/admin/product";
        }
        model.addAttribute("newProduct", product.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String UpdateProductPage(@ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult bindingResult,
            @RequestParam(value = "hao_File", required = false) MultipartFile file) {
        Optional<Product> product = this.productService.FindProductById(newProduct.getId());

        if (bindingResult.hasErrors()) {
            return "admin/product/update";
        }
        if (product.isEmpty()) {
            return "redirect:/admin/product";
        }
        Product existingProduct = product.get();
        if (file != null && !file.isEmpty()) {
            String avatar = this.uploadFileService.handleSaveUpLoadFile(file, "product");
            existingProduct.setImage(avatar);

        }
        existingProduct.setName(newProduct.getName());
        existingProduct.setDetailDesc(newProduct.getDetailDesc());
        existingProduct.setFactory(newProduct.getFactory());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setShortDesc(newProduct.getShortDesc());
        existingProduct.setQuantity(newProduct.getQuantity());
        existingProduct.setTarget(newProduct.getTarget());
        this.productService.SaveProduct(existingProduct);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String DeleteProductPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String DeleteProductPage(@RequestParam Long id) {
        Optional<Product> product = this.productService.FindProductById(id);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            this.productService.DeleteProductById(existingProduct.getId());
        }
        return "redirect:/admin/product";
    }
}
