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
public class BakedBeanRule implements IRule {
	
	@Autowired
	IProductRepository productRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(BakedBeanRule.class);
	
	private final String BAKED_BEAN = "Baked Beans";

	@Override
	public int executeRule(Map<String, ProductItemDetail> productDetailMap) {
		int flag = 0;
		ProductItemDetail productItemDetail = productDetailMap.get(BAKED_BEAN.toLowerCase());
		
		if(productItemDetail!=null && (productItemDetail.getPurchasedQuantity()>=3)) {
			
			logger.info("There is a 4 for 3 offer on Baked beans");
			//Product prod = productRepository.findByProductName("Baked Beans");
			
			long freeQuantity = productItemDetail.getPurchasedQuantity()/3;
						
			logger.info("freeQuantity:->",freeQuantity);
			flag = addFreeProduct(productItemDetail,freeQuantity,productDetailMap);
			
			//flag = 1;
		
			
		}
		
		return flag;
	}
	
	@Override
	public String getRuleName() {
		// TODO Auto-generated method stub
		return "BakedBeanRule";
	}
}
