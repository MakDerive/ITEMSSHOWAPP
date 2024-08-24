package com.example.fleetapp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.fleetapp.models.User;
import com.example.fleetapp.models.enums.Role;
import com.example.fleetapp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String userEmail = user.getEmail();
        if (userRepository.findByEmail(userEmail) != null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new User with email: {}", userEmail);
        userRepository.save(user);
        return true;
    }
    
    public List<User> list(){
    	return userRepository.findAll();
    }
    
    public void banUser(Long id) {
    	User user = userRepository.findById(id).orElse(null);
    	if(user !=null) {
    		if(user.isActive()) {
    			user.setActive(false);
    		} else {
    			user.setActive(true);
    		}
    		log.info("Ban user with id = {}; email: {}",user.getId(),user.getEmail());
    	}
    	userRepository.save(user);
    }
    
    public void changeUserRoles(User user,Map<String,String> form) {
    	Set<String> roles= Arrays.stream(Role.values())
    			.map(Role::name)
    			.collect(Collectors.toSet());
    	user.getRoles().clear();
    	for(String key : form.keySet()) {
    		if (roles.contains(key)) {
    			user.getRoles().add(Role.valueOf(key));
    		}
    	}
    	userRepository.save(user);
    	
    }
}