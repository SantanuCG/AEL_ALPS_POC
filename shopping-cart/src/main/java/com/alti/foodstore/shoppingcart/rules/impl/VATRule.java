package com.alti.foodstore.shoppingcart.rules.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.rules.IRule;

@Component
public class VATRule implements IRule{
	
	public static final Logger logger = LoggerFactory.getLogger(VATRule.class);
	
	private final String INSURANCE = "Insurance";

	@Override
	public int executeRule(Map<String, ProductItemDetail> productDetailMap) {
		
		int flag = 0;

		 for (Map.Entry<String, ProductItemDetail> entry : productDetailMap.entrySet()) {
				ProductItemDetail prodObj = (ProductItemDetail)entry.getValue();
				if(!prodObj.getProductCategory().equalsIgnoreCase(INSURANCE)) {
					logger.info("a 14% VAT is added to all purchases, BUT insurance products are exempt");
					prodObj.setVat(14.0);
					productDetailMap.replace(prodObj.getProductName().toLowerCase(), prodObj);
					
					flag=1;
				}
		  
		}
		return flag;
	}
	
	@Override
	public String getRuleName() {
		// TODO Auto-generated method stub
		return "VATRule";
	}

}
