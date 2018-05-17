package com.alti.foodstore.shoppingcart.rules.impl;

import java.util.Map;

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
		int flag = 0;
		
		boolean hasElectronicItem = isHavingElectronicAppliance(productDetailMap);
		
		for (Map.Entry<String, ProductItemDetail> entry : productDetailMap.entrySet()) {
				ProductItemDetail prodObj = (ProductItemDetail) entry.getValue();
				if (prodObj.getProductCategory().equalsIgnoreCase(INSURANCE)) {
					if(hasElectronicItem) {
							logger.info("Insurance is discounted 20% if you buy any type of electric appliance");
							prodObj.setDiscount(20.0);
							productDetailMap.replace(prodObj.getProductName().toLowerCase(), prodObj);
							flag=1;
						}
				}
					
		}

				
		return flag;
	}
	
	private boolean isHavingElectronicAppliance(Map<String, ProductItemDetail> productDetailMap) {
		boolean flag = false;

		for (Map.Entry<String, ProductItemDetail> entry : productDetailMap.entrySet()) {
			ProductItemDetail prodObj = (ProductItemDetail) entry.getValue();
			if (prodObj.getProductCategory().equalsIgnoreCase(ELECTRONICS)) {
				flag = true;
				break;
			}

		}

		return flag;
	}
	
	@Override
	public String getRuleName() {
		// TODO Auto-generated method stub
		return "InsuranceRule";
	}


}
