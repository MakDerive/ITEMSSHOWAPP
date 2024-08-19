package com.example.fleetapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fleetapp.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findByTitle(String title);
}
