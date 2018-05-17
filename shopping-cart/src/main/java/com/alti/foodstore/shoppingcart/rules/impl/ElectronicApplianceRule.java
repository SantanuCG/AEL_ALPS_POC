package com.alti.foodstore.shoppingcart.rules.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.rules.IRule;

@Component
public class ElectronicApplianceRule implements IRule {

	public static final Logger logger = LoggerFactory.getLogger(ElectronicApplianceRule.class);

	public static final String ELECTRONICS = "Electronics";

	@Override
	public int executeRule(Map<String, ProductItemDetail> productDetailMap) {
		int flag = 0;
		long totalCount = getElectronicApplianceCount(productDetailMap);

		for (Map.Entry<String, ProductItemDetail> entry : productDetailMap.entrySet()) 
		{
			ProductItemDetail prodObj = (ProductItemDetail) entry.getValue();
			
			if (prodObj.getProductCategory().equalsIgnoreCase(ELECTRONICS) && totalCount <= 5) {
				logger.info("There is a 7% discount if you buy more than 1 Electric appliance");
				prodObj.setDiscount(7.0);
				productDetailMap.replace(prodObj.getProductName().toLowerCase(), prodObj);
				
				flag = 1;
			}else if(prodObj.getProductCategory().equalsIgnoreCase(ELECTRONICS) && totalCount > 5) {
				logger.info("You are not able to buy more than 5 electric appliances (In total across types) in a single purchase");
				prodObj.setPrice(0.0);
				prodObj.setMessage("You are not able to buy more than 5 electric appliances (In total across types) in a single purchase");
				productDetailMap.replace(prodObj.getProductName().toLowerCase(), prodObj);
				
				flag = 1;

			}

		}
		return flag;
	}

	private long getElectronicApplianceCount(Map<String, ProductItemDetail> productDetailMap) {
		long totalCount = 0;

		for (Map.Entry<String, ProductItemDetail> entry : productDetailMap.entrySet()) {
			ProductItemDetail prodObj = (ProductItemDetail) entry.getValue();
			if (prodObj.getProductCategory().equalsIgnoreCase(ELECTRONICS)) {
				totalCount += 1;
			}

		}

		return totalCount;
	}
	
	@Override
	public String getRuleName() {
		// TODO Auto-generated method stub
		return "ElectronicApplianceRule";
	}

}
