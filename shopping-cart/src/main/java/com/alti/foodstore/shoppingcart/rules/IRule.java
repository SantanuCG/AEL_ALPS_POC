package com.alti.foodstore.shoppingcart.rules;

import java.util.Map;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;



public interface IRule {

	public String getRuleName();
	
	public int executeRule(Map<String,ProductItemDetail> productDetailMap);

	default int addFreeProduct(ProductItemDetail productItemDetail,long freeQuantity, Map<String, ProductItemDetail> productDetailMap) {
		ProductItemDetail productItemDetailObj = productDetailMap.entrySet().stream()
				.filter(e -> e.getKey().equals(productItemDetail.getProductName().toLowerCase()))
				.map(Map.Entry::getValue)
				.findFirst() 
		 		.orElse(null);
		
		if(productItemDetailObj != null) {
			freeQuantity += productItemDetailObj.getFreeQuantity();
		}
		
		productItemDetail.setFreeQuantity(freeQuantity);
		productItemDetail.setConsideredInTotalAmount(false);
		productItemDetail.setMessage(freeQuantity+" quantity of "+productItemDetail.getProductName()+" added for FREE!!!");
		productDetailMap.put(productItemDetail.getProductName().toLowerCase(), productItemDetail);
				
		return 1;
	}
}
