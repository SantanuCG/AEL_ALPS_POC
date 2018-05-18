package com.alti.foodstore.shoppingcart.rules.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.repository.IProductRepository;
import com.alti.foodstore.shoppingcart.rules.IRule;

@Component
public class BreadLoafRule implements IRule {
	
	@Autowired
	IProductRepository productRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(BreadLoafRule.class);
	
	private final String BREAD_LOAF = "loaf of bread";

	@Override
	public int executeRule(Map<String, ProductItemDetail> productDetailMap) {
		int flag = 0;
		
		ProductItemDetail productItemDetail = productDetailMap.get(BREAD_LOAF);
		
		if(productItemDetail!= null && (productItemDetail.getPurchasedQuantity()>=1)) {
			
			logger.info("There is a 2 for 1 offer on Loaf of Bread");
						
			long freeQuantity = productItemDetail.getPurchasedQuantity()*2;
						
			logger.info("freeQuantity:->",freeQuantity);
			flag = addFreeProduct(productItemDetail,freeQuantity,productDetailMap);
			
					
		}
		
		return flag;
	}

	@Override
	public String getRuleName() {
		return "BreadLoafRule";
	}

}
