package com.alti.foodstore.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alti.foodstore.shoppingcart.entities.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {
	
	public Product findByProductName(String productName);

}
