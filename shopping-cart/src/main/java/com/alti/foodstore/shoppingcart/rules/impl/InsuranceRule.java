package com.alti.foodstore.shoppingcart.rules.impl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.rules.IRule;

@Component
public class InsuranceRule implements IRule {
	
	public static final Logger logger = LoggerFactory.getLogger(InsuranceRule.class);
	
	private final String INSURANCE = "Insurance";
	public static final String ELECTRONICS = "Electronics";

	@Override
	public int executeRule(Map<String, ProductItemDetail> productDetailMap) {
		AtomicInteger flag = new AtomicInteger(0);
		
		boolean hasElectronicItem = isHavingElectronicAppliance(productDetailMap);
		
		productDetailMap.forEach((key,prodObj) -> {
			if(StringUtils.equalsIgnoreCase(prodObj.getProductCategory(), INSURANCE) && hasElectronicItem) {
				logger.info("Insurance is discounted 20% if you buy any type of electric appliance");
				prodObj.setDiscount(20.0);
				productDetailMap.replace(prodObj.getProductName().toLowerCase(), prodObj);
				flag.set(1);
			}
			
		});
		
						
		return flag.get();
	}
	
	private boolean isHavingElectronicAppliance(Map<String, ProductItemDetail> productDetailMap) {
				
		return productDetailMap.entrySet().stream().anyMatch(v -> StringUtils.equalsIgnoreCase(v.getValue().getProductCategory(), ELECTRONICS));
		
		
	}
	
	@Override
	public String getRuleName() {
		return "InsuranceRule";
	}


}
