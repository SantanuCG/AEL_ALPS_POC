package com.alti.foodstore.shoppingcart.rules.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.rules.impl.VATRule;

@RunWith(MockitoJUnitRunner.class)
public class VATRuleTest {
	
	@InjectMocks
	VATRule vATRule;
	
		
    @Before
	public void setupMock() {
	       MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testPorkSausageRule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail porkSausageProd = new ProductItemDetail();
		porkSausageProd.setProductId(Long.valueOf(1));
		porkSausageProd.setProductCategory("Food");
		porkSausageProd.setProductName("Pork Sausages");
		porkSausageProd.setPerUnitQty(Long.valueOf(1));
		porkSausageProd.setPurchasedQuantity(Long.valueOf(1));
		porkSausageProd.setPrice(6.00);
		productMap.put(porkSausageProd.getProductName().toLowerCase(), porkSausageProd);
		
		ProductItemDetail insuranceProd = new ProductItemDetail();
		insuranceProd.setProductId(Long.valueOf(2));
		insuranceProd.setProductCategory("Insurance");
		insuranceProd.setProductName("Insurance per Electric Appliance");
		insuranceProd.setPerUnitQty(Long.valueOf(1));
		insuranceProd.setPurchasedQuantity(Long.valueOf(1));
		insuranceProd.setPrice(120.00);
		productMap.put(insuranceProd.getProductName().toLowerCase(), insuranceProd);
		
		int result = vATRule.executeRule(productMap);
		
		ProductItemDetail porkSausageObj = productMap.get("Pork Sausages".toLowerCase());
		assertNotNull(porkSausageObj);
		assertEquals(Double.valueOf(14.00),porkSausageObj.getVat());
		
		ProductItemDetail insuranceProdObj = productMap.get("Insurance per Electric Appliance".toLowerCase());
		assertNotNull(insuranceProdObj);
		assertEquals(Double.valueOf(0.0),insuranceProdObj.getVat());
		
		assertEquals(1,result);
		
		
		
	}

}
