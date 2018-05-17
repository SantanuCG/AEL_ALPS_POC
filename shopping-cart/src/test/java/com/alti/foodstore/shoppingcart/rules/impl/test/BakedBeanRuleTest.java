package com.alti.foodstore.shoppingcart.rules.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.rules.IRule;
import com.alti.foodstore.shoppingcart.rules.impl.BakedBeanRule;

@RunWith(MockitoJUnitRunner.class)
public class BakedBeanRuleTest {
	
	@InjectMocks
	IRule bakedBeanRule;
	
    @Before
	public void setupMock() {
	       MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testBakedBeanRule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail productItemDetail = new ProductItemDetail();
		productItemDetail.setProductId(Long.valueOf(1));
		productItemDetail.setProductCategory("Food");
		productItemDetail.setProductName("Baked Beans");
		productItemDetail.setPerUnitQty(Long.valueOf(1));
		productItemDetail.setPurchasedQuantity(Long.valueOf(1));
		Double price = productItemDetail.getPrice() / productItemDetail.getPerUnitQty() * productItemDetail.getPurchasedQuantity();
		productItemDetail.setPrice(price);
		productMap.put(productItemDetail.getProductName().toLowerCase(), productItemDetail);
		
		Mockito.when(bakedBeanRule.addFreeProduct(productItemDetail,1,productMap)).thenReturn(1);
		int result = bakedBeanRule.executeRule(productMap);
		
		ProductItemDetail updatedProductItemDetail = productMap.get("Baked Beans".toLowerCase());
		assertNotNull(updatedProductItemDetail);
		assertEquals(1,result);
		assertEquals(1,updatedProductItemDetail.getFreeQuantity());
		
		
		
	}

}
