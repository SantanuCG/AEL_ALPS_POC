package com.alti.foodstore.shoppingcart.rules.impl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
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
		//int flag = 0;
		long totalCount = getElectronicApplianceCount(productDetailMap);

		AtomicInteger flag = new AtomicInteger(0);
		
		productDetailMap.forEach((key,prodObj) -> {
					
			if (StringUtils.equalsIgnoreCase(prodObj.getProductCategory(),ELECTRONICS) && (totalCount >1 && totalCount <= 5)) {
				logger.info("There is a 7% discount if you buy more than 1 Electric appliance");
				prodObj.setDiscount(7.0);
				productDetailMap.put(prodObj.getProductName().toLowerCase(), prodObj);
				flag.set(1);
			}else if(StringUtils.equalsIgnoreCase(prodObj.getProductCategory(),ELECTRONICS) && totalCount > 5) {
				logger.info("You are not able to buy more than 5 electric appliances (In total across types) in a single purchase");
				prodObj.setConsideredInTotalAmount(false);
				prodObj.setMessage("You are not able to buy more than 5 electric appliances (In total across types) in a single purchase");
				productDetailMap.put(prodObj.getProductName().toLowerCase(), prodObj);
				flag.set(1);

			}

		});
		
		return flag.intValue();
	}

	private long getElectronicApplianceCount(Map<String, ProductItemDetail> productDetailMap) {
			
		AtomicLong totalCount = new AtomicLong(0);
		
		productDetailMap.forEach((key,prodObj) -> {
			totalCount.getAndAdd(prodObj.getPurchasedQuantity());
		});

		return totalCount.get();
	}
	
	@Override
	public String getRuleName() {
		return "ElectronicApplianceRule";
	}

}
