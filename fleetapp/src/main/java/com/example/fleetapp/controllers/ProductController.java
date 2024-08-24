package com.example.fleetapp.controllers;

import java.io.IOException;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.fleetapp.models.Product;
import com.example.fleetapp.services.ProductService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	
	@GetMapping("/")
	public String products(@RequestParam(name="title",required = false) String title, Principal principal,Model model) {
		model.addAttribute("products",productService.listProducts(title));
		model.addAttribute("user",productService.getUserByPrincipal(principal));
		return "products";
	}
	
	@GetMapping("/product/{id}")
	public String productInfo(@PathVariable Long id,Model model) {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		
		model.addAttribute("images", product.getImages());
		return "product-info";
	}
	
	@PostMapping("/product/create")
	public String createProduct(@RequestParam MultipartFile file1,
			@RequestParam MultipartFile file2, @RequestParam MultipartFile file3, Product product,Principal principal) throws IOException {
		productService.saveProduct(principal,product, file1, file2, file3);
		return "redirect:/";
	}
	
	@PostMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/";
	}
	
	
}
