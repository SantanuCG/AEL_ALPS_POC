package com.alti.foodstore.shoppingcart.rules;

import java.util.Map;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;



public interface IRule {

	public String getRuleName();
	
	public int executeRule(Map<String,ProductItemDetail> productDetailMap);

	default int addFreeProduct(ProductItemDetail productItemDetail,long freeQuantity, Map<String, ProductItemDetail> productDetailMap) {
		if(productDetailMap.get(productItemDetail.getProductName().toLowerCase())==null) {
			//Product doesn't exist, so add
			productItemDetail.setFreeQuantity(freeQuantity);
			productItemDetail.setMessage(freeQuantity+" quantity of "+productItemDetail.getProductName()+" added for FREE!!!");
			productDetailMap.put(productItemDetail.getProductName().toLowerCase(), productItemDetail);
		}else {
			//Product doesn't exist, so replace
			ProductItemDetail productItemDetailObj = (ProductItemDetail)productDetailMap.get(productItemDetail.getProductName().toLowerCase());
			long freeQty = productItemDetailObj.getFreeQuantity() + freeQuantity;
			productItemDetail.setFreeQuantity(freeQty);
			productItemDetail.setMessage(freeQty+" quantity of "+productItemDetail.getProductName()+" added for FREE!!!");
			productDetailMap.replace(productItemDetailObj.getProductName().toLowerCase(), productItemDetailObj);
		}
		
		return 1;
	}
}
