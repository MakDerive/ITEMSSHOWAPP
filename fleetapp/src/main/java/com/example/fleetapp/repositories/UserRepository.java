package com.example.fleetapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fleetapp.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
