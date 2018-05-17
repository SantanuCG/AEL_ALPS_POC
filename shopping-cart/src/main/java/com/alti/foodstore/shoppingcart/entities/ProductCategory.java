package com.alti.foodstore.shoppingcart.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTCATEGORY")
public class ProductCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2133431L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "CATEGORYID")
	private Long categoryId;
	
	@Column(name = "CATEGORYNAME")
	private String categoryName;

	@OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products = new ArrayList<>();

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		products.add(product);
		product.setProductCategory(this);
	}

	public void removeProduct(Product product) {
		products.remove(product);
		product.setProductCategory(null);
	}
}
