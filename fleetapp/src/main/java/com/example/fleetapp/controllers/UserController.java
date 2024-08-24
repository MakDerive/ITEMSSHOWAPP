package com.example.fleetapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.fleetapp.models.User;
import com.example.fleetapp.models.enums.Role;
import com.example.fleetapp.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@PostMapping("/registration")
	public String creteUser(User user,Model model) {
		if(!userService.createUser(user)) {
			model.addAttribute("errorMessage","email: " + user.getEmail()+" уже сущесвтует");
			return "registration";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/user/{user}")
	public String userInfo(@PathVariable("user") User user,Model model) {
		model.addAttribute("user",user);
		model.addAttribute("products",user.getProducts());
		return "user-info";
	}
	
	
}
