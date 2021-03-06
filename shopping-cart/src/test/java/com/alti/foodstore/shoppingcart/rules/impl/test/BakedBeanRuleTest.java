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
public class BakedBeanRuleTest {
	
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
	public void testBakedBeanRule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail productItemDetail = new ProductItemDetail();
		productItemDetail.setProductId(Long.valueOf(1));
		productItemDetail.setProductCategory("Food");
		productItemDetail.setProductName("Baked Beans");
		productItemDetail.setPerUnitQty(Long.valueOf(1));
		productItemDetail.setPurchasedQuantity(Long.valueOf(3));
		productItemDetail.setPrice(1.30);
		productMap.put(productItemDetail.getProductName().toLowerCase(), productItemDetail);
		
		rulesExecutorMock.execute(productMap);
		
		ProductItemDetail updatedProductItemDetail = productMap.get("Baked Beans".toLowerCase());
		assertNotNull(updatedProductItemDetail);
		assertEquals(1,updatedProductItemDetail.getFreeQuantity());
		assertEquals(4,updatedProductItemDetail.getTotalQuantity());
		
		
		
	}

}
