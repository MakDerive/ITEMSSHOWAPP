package com.example.fleetapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fleetapp.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
