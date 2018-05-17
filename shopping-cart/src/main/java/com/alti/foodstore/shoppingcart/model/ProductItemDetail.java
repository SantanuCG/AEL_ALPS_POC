package com.alti.foodstore.shoppingcart.model;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ProductItemDetail" , description = "Product Information Details")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductItemDetail extends ProductItem {

	@JsonProperty("freeQuantity")
	private long freeQuantity=0;
	
	@JsonProperty("purchasedQuantity")
	private long purchasedQuantity=0;
	
	@JsonProperty("totalQuantity")
	private long totalQuantity=0;

	@JsonProperty("vat")
	private Double vat=0.0;
	
	@JsonProperty("discount")
	private Double discount=0.0;
	
	@JsonProperty("total-amount")
	private Double totalAmount=0.0;
	
	@JsonProperty("message")
	private String message;

		

	/**
	 * @return the freeQuantity
	 */
	@JsonProperty("freeQuantity")
	public long getFreeQuantity() {
		return freeQuantity;
	}

	/**
	 * @param freeQuantity the freeQuantity to set
	 */
	@JsonProperty("freeQuantity")
	public void setFreeQuantity(long freeQuantity) {
		this.freeQuantity = freeQuantity;
	}

	/**
	 * @return the purchasedQuantity
	 */
	@JsonProperty("purchasedQuantity")
	public long getPurchasedQuantity() {
		return purchasedQuantity;
	}

	/**
	 * @param purchasedQuantity the purchasedQuantity to set
	 */
	@JsonProperty("purchasedQuantity")
	public void setPurchasedQuantity(long purchasedQuantity) {
		this.purchasedQuantity = purchasedQuantity;
	}

	/**
	 * @return the totalQuantity
	 */
	@JsonProperty("totalQuantity")
	public long getTotalQuantity() {
		totalQuantity = purchasedQuantity + freeQuantity;
		return totalQuantity;
	}

	/**
	 * @param totalQuantity the totalQuantity to set
	 */
	@JsonProperty("totalQuantity")
	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/**
	 * @return the vat
	 */
	@JsonProperty("vat")
	public Double getVat() {
		return vat;
	}

	/**
	 * @param vat
	 *            the vat to set
	 */
	@JsonProperty("vat")
	public void setVat(Double vat) {
		this.vat = vat;
	}

	/**
	 * @return the discount
	 */
	@JsonProperty("discount")
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	@JsonProperty("discount")
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the totalAmount
	 */
	@JsonProperty("total-amount")
	public Double getTotalAmount() {
		totalAmount= (purchasedQuantity/perUnitQty*price);
		totalAmount+=totalAmount*(vat>0?vat/100:0);
		totalAmount-=totalAmount*(discount>0?discount/100:0);
		
		DecimalFormat df = new DecimalFormat("######.##");
		
		return Double.valueOf(df.format(totalAmount));
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	@JsonProperty("total-amount")
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	

}
