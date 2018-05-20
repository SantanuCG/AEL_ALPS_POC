package com.alti.foodstore.shoppingcart.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.util.concurrent.AtomicDouble;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "OrderReceipt" , description = "Receipt of Products Ordered")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderReceipt {

	@JsonProperty("order-details")
	private List<ProductItemDetail> orderDetailList;
	@JsonProperty("grandTotal")
	private Double grandTotal;
	
		
	/**
	 * @return the orderDetailList
	 */
	@JsonProperty("order-details")
	public List<ProductItemDetail> getOrderDetailList() {
		if(orderDetailList==null) {
			orderDetailList = new ArrayList<>();
		}
		return orderDetailList;
	}
	/**
	 * @param orderDetailList the orderDetailList to set
	 */
	@JsonProperty("order-details")
	public void setOrderDetailList(List<ProductItemDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	/**
	 * @return the grandTotal
	 */
	@JsonProperty("grandTotal")
	public Double getGrandTotal() {
		AtomicDouble gT = new AtomicDouble(0.00);
		
		orderDetailList.forEach(productItemDetail->{
			          gT.getAndAdd(productItemDetail.getTotalAmount());
			        });
		
		grandTotal = gT.get();
		
		DecimalFormat df = new DecimalFormat("######.##");
		
		return Double.valueOf(df.format(grandTotal));
	}
	/**
	 * @param grandTotal the grandTotal to set
	 */
	@JsonProperty("grandTotal")
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	
	
	
}
