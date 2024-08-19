package com.example.fleetapp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.fleetapp.models.Image;
import com.example.fleetapp.models.Product;
import com.example.fleetapp.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepositroy;
	
	public List<Product> listProducts(String title){
		if (title !=null) return productRepositroy.findByTitle(title);
		return productRepositroy.findAll();
	}
	
	public void saveProduct(Product product,MultipartFile file1,MultipartFile file2,MultipartFile file3) throws IOException {
		Image image1;
		Image image2;
		Image image3;
		try {
			if(file1.getSize() != 0) {
				image1 = toImageEntity(file1);
				image1.setPreviewImage(true);
				
				product.addImageToProduct(image1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			if(file2.getSize() != 0) {
				image2 = toImageEntity(file2);
				
				product.addImageToProduct(image2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			if(file3.getSize() != 0) {
				image3 = toImageEntity(file3);
				
				product.addImageToProduct(image3);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.info("Saving new Product. Title: {}; Author: {}",product.getTitle(),product.getAuthor());
		Product productFromDb = productRepositroy.save(product);
		productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
		productRepositroy.save(product);
	}
	
	private Image toImageEntity(MultipartFile file) throws IOException {
		Image image = new Image(); 
		image.setName(file.getName());
		image.setOriginalFileName(file.getOriginalFilename());
		image.setContentType(file.getContentType());
		image.setSize(file.getSize());
		image.setBytes(file.getBytes());
		return image;
	}
	
	public void deleteProduct(Long id) {
		productRepositroy.deleteById(id);
	}
	
	public Product getProductById(Long id) {
		return productRepositroy.findById(id).orElse(null);
	}
}
