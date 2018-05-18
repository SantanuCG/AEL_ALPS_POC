package com.alti.foodstore.shoppingcart.rules.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.rules.IRule;
import com.alti.foodstore.shoppingcart.rules.executor.impl.RulesExecutor;
import com.alti.foodstore.shoppingcart.rules.impl.BakedBeanRule;
import com.alti.foodstore.shoppingcart.rules.impl.BreadLoafRule;
import com.alti.foodstore.shoppingcart.rules.impl.ElectronicApplianceRule;
import com.alti.foodstore.shoppingcart.rules.impl.InsuranceRule;
import com.alti.foodstore.shoppingcart.rules.impl.ParacetamolRule;
import com.alti.foodstore.shoppingcart.rules.impl.PorkSausageRule;
import com.alti.foodstore.shoppingcart.rules.impl.VATRule;

@RunWith(MockitoJUnitRunner.class)
public class ElectronicApplianceRuleTest {
	
	@InjectMocks
	RulesExecutor rulesExecutorMock;
	
    @Before
	public void setupMock() {
    	List<IRule> ruleList= new ArrayList<>();
		ruleList.add(new BakedBeanRule());
		ruleList.add(new PorkSausageRule());
		ruleList.add(new InsuranceRule());
		ruleList.add(new ElectronicApplianceRule());
		ruleList.add(new VATRule());
		ruleList.add(new BreadLoafRule());
		ruleList.add(new ParacetamolRule());
		
		rulesExecutorMock = new RulesExecutor(ruleList);
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testElectronicAppliancefLessthan5Rule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail prodTVObj = new ProductItemDetail();
		prodTVObj.setProductId(Long.valueOf(1));
		prodTVObj.setProductCategory("Electronics");
		prodTVObj.setProductName("TV 25 inch XamZung");
		prodTVObj.setPerUnitQty(Long.valueOf(1));
		prodTVObj.setPurchasedQuantity(Long.valueOf(2));
		prodTVObj.setPrice(180.00);
		productMap.put(prodTVObj.getProductName().toLowerCase(), prodTVObj);
		
		ProductItemDetail prodMicrowaveObj = new ProductItemDetail();
		prodMicrowaveObj.setProductId(Long.valueOf(1));
		prodMicrowaveObj.setProductCategory("Electronics");
		prodMicrowaveObj.setProductName("Microwave Oven");
		prodMicrowaveObj.setPerUnitQty(Long.valueOf(1));
		prodMicrowaveObj.setPurchasedQuantity(Long.valueOf(2));
		prodMicrowaveObj.setPrice(57.00);
		productMap.put(prodMicrowaveObj.getProductName().toLowerCase(), prodMicrowaveObj);
		
		rulesExecutorMock.execute(productMap);
		
		ProductItemDetail tVProductItemDetail = productMap.get("TV 25 inch XamZung".toLowerCase());
		assertNotNull(tVProductItemDetail);
		assertEquals(Double.valueOf(7.00),tVProductItemDetail.getDiscount());
		
		
		ProductItemDetail microWProductItemDetail = productMap.get("Microwave Oven".toLowerCase());
		assertNotNull(microWProductItemDetail);
		assertEquals(Double.valueOf(7.00),microWProductItemDetail.getDiscount());
		
	}
	
	@Test
	public void testElectronicAppliancefMorethan5Rule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail prodTVObj = new ProductItemDetail();
		prodTVObj.setProductId(Long.valueOf(1));
		prodTVObj.setProductCategory("Electronics");
		prodTVObj.setProductName("TV 25 inch XamZung");
		prodTVObj.setPerUnitQty(Long.valueOf(1));
		prodTVObj.setPurchasedQuantity(Long.valueOf(3));
		prodTVObj.setPrice(180.00);
		productMap.put(prodTVObj.getProductName().toLowerCase(), prodTVObj);
		
		ProductItemDetail prodMicrowaveObj = new ProductItemDetail();
		prodMicrowaveObj.setProductId(Long.valueOf(1));
		prodMicrowaveObj.setProductCategory("Electronics");
		prodMicrowaveObj.setProductName("Microve Oven");
		prodMicrowaveObj.setPerUnitQty(Long.valueOf(1));
		prodMicrowaveObj.setPurchasedQuantity(Long.valueOf(3));
		prodMicrowaveObj.setPrice(57.00);
		productMap.put(prodMicrowaveObj.getProductName().toLowerCase(), prodMicrowaveObj);
		
		rulesExecutorMock.execute(productMap);
		
		ProductItemDetail tVProductItemDetail = productMap.get("TV 25 inch XamZung".toLowerCase());
		assertNotNull(tVProductItemDetail);
		assertNotNull(tVProductItemDetail.getMessage());
		assertEquals(Double.valueOf(0.00),tVProductItemDetail.getTotalAmount());
		
		
		
		
		
	}

}
