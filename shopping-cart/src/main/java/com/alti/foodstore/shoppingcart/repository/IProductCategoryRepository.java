package com.alti.foodstore.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alti.foodstore.shoppingcart.entities.ProductCategory;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	
	public ProductCategory findByCategoryName(String categoryName);

}
