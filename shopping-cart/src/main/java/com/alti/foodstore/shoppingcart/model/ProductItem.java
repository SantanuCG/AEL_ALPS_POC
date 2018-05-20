package com.alti.foodstore.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ProductItem" , description = "Product Information Summary")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductItem {

	@JsonIgnore
	protected Long productId;
	
	@JsonProperty("productName")
	protected String productName;
	
	@JsonProperty("perUnitQty")
	protected long perUnitQty=0;
	
	@JsonProperty("price")
	protected Double price=0.0;
	
	@JsonProperty("productCategory")
	protected String productCategory;

	public ProductItem() {
		
	}
	
	public ProductItem(Long productId, String productName, long perUnitQty, Double price, String productCategory) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.perUnitQty = perUnitQty;
		this.price = price;
		this.productCategory = productCategory;
	}

	/**
	 * @return the productId
	 */
	@JsonProperty("productId")
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	@JsonProperty("productName")
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	@JsonProperty("productName")
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the price
	 */
	@JsonProperty("price")
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	@JsonProperty("price")
	public void setPrice(Double price) {
		this.price = price;
	}

	

	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}

	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * @return the perUnitQty
	 */
	@JsonProperty("perUnitQty")
	public long getPerUnitQty() {
		return perUnitQty;
	}

	/**
	 * @param perUnitQty the perUnitQty to set
	 */
	@JsonProperty("perUnitQty")
	public void setPerUnitQty(long perUnitQty) {
		this.perUnitQty = perUnitQty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (perUnitQty ^ (perUnitQty >>> 32));
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
		if (!(obj instanceof ProductItem))
			return false;
		ProductItem other = (ProductItem) obj;
		if (perUnitQty != other.perUnitQty)
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
