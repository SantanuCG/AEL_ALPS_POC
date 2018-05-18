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
public class ParacetamolRule implements IRule{
	
	public static final String PARACETAMOL_10TAB = "Paracetamol XYZ 10 Tablets";
	public static final String PARACETAMOL_20TAB = "Paracetamol XYZ 20 Tablets";
	public static final String PARACETAMOL = "Paracetamol";
	
	@Autowired
	IProductRepository productRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(ParacetamolRule.class);

	@Override
	public int executeRule(Map<String, ProductItemDetail> productDetailMap)
	{
		int flag = 0;

		long totalParacetamolCount = getTotalParacetamolCount(productDetailMap);
			
	    ProductItemDetail productItemDetail = productDetailMap.get(PARACETAMOL_10TAB.toLowerCase());
		
	    if(productItemDetail !=null) {
	    	if(totalParacetamolCount > 20) {
				logger.error("The law says that you cannot buy more than 20 tablets of paracetamol in the same purchase");
				productItemDetail.setMessage("The law says that you cannot buy more than 20 tablets of paracetamol in the same purchase");
				productItemDetail.setPrice(0.0);
				productDetailMap.replace(productItemDetail.getProductName().toLowerCase(), productItemDetail);
				flag = 1;
				
	    	}else if(productItemDetail.getPurchasedQuantity()==10) {
				logger.info("Paracetamol XYZ 10 Tablets on their own are Buy One Get One Free");
				long freeQuantity = productItemDetail.getPurchasedQuantity();
				flag = addFreeProduct(productItemDetail,freeQuantity,productDetailMap);
				totalParacetamolCount+=freeQuantity;
				//flag = 1;
			}
	    	
	    }
	    
	    productItemDetail = null;
	    
	    productItemDetail = productDetailMap.get(PARACETAMOL_20TAB.toLowerCase());
	    if(productItemDetail !=null) {
		    if(totalParacetamolCount > 20) {
				logger.error("The law says that you cannot buy more than 20 tablets of paracetamol in the same purchase");
				productItemDetail.setMessage("The law says that you cannot buy more than 20 tablets of paracetamol in the same purchase");
				productItemDetail.setPrice(0.0);
				productDetailMap.replace(productItemDetail.getProductName().toLowerCase(), productItemDetail);
				flag = 1;
				
	    	}
	    }
			
		
		return flag;
	}
	
	private long getTotalParacetamolCount(Map<String, ProductItemDetail> productDetailMap) {
		long totalParacetamolCount = 0;
		
	    for (Map.Entry<String, ProductItemDetail> entry : productDetailMap.entrySet()) {
				ProductItemDetail prodObj = (ProductItemDetail)entry.getValue();
				if(prodObj.getProductCategory().equalsIgnoreCase(PARACETAMOL)) {
					totalParacetamolCount+=prodObj.getTotalQuantity();
			}
			  
		}
	    
	    return totalParacetamolCount;
	}
	
	@Override
	public String getRuleName() {
		return "ParacetamolRule";
	}

}
