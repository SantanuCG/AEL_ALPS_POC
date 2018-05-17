package com.alti.foodstore.shoppingcart.rules.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alti.foodstore.shoppingcart.entities.Product;
import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.repository.IProductRepository;
import com.alti.foodstore.shoppingcart.rules.IRule;

@Component
public class PorkSausageRule implements IRule {
	
	@Autowired
	IProductRepository productRepository;
	
	//@Value("${free.bakedbean.qty}")
	private long freeQuantity;
	
	private final String PORK_SUSAGE = "Pork Sausages";
	private final String BAKED_BEAN = "Baked Beans";
	
	public static final Logger logger = LoggerFactory.getLogger(PorkSausageRule.class);

	@Override
	public int executeRule(Map<String, ProductItemDetail> productDetailMap) {
		int flag = 0;
		
		ProductItemDetail productItemDetail = productDetailMap.get(PORK_SUSAGE.toLowerCase());
		
		if(productItemDetail!=null) {
			logger.info("One tin of Baked Beans is added for free for each Packet of Pork Sausages sold");
			freeQuantity = productItemDetail.getPurchasedQuantity();
			
			Product prod = productRepository.findByProductName(BAKED_BEAN);
			ProductItemDetail productItemDetailObj = new ProductItemDetail();
			productItemDetailObj.setProductId(prod.getProductId());
			productItemDetailObj.setProductName(prod.getProductName());
			productItemDetailObj.setProductCategory(prod.getProductCategory().getCategoryName());
			productItemDetailObj.setPrice(prod.getPrice());
			productItemDetailObj.setPerUnitQty(prod.getPerUnitQty());
			//Add free baked bean
			flag = addFreeProduct(productItemDetailObj,freeQuantity,productDetailMap);
			
			//flag = 1;
		}
		return flag;
	}

	
	@Override
	public String getRuleName() {
		// TODO Auto-generated method stub
		return "PorkSausageRule";
	}
	

}
