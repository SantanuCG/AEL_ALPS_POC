package com.alti.foodstore.shoppingcart.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1123221L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "PRODUCTID")
	private Long productId;
	
	@Column(name = "PRODUCTNAME")
	private String productName;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "PERUNITQTY")
	private Long perUnitQty;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORYID")
	private ProductCategory productCategory;

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the productCategory
	 */
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * @param productCategory
	 *            the productCategory to set
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * @return the perUnitQty
	 */
	public Long getPerUnitQty() {
		return perUnitQty;
	}

	/**
	 * @param perUnitQty the perUnitQty to set
	 */
	public void setPerUnitQty(Long perUnitQty) {
		this.perUnitQty = perUnitQty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((perUnitQty == null) ? 0 : perUnitQty.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (perUnitQty == null) {
			if (other.perUnitQty != null)
				return false;
		} else if (!perUnitQty.equals(other.perUnitQty))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		return true;
	}

	

}
