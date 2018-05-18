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
public class InsuranceRuleTest {

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
	public void testInsuranceRule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail insuranceObj = new ProductItemDetail();
		insuranceObj.setProductId(Long.valueOf(1));
		insuranceObj.setProductCategory("Insurance");
		insuranceObj.setProductName("Insurance per Electric Appliance");
		insuranceObj.setPerUnitQty(Long.valueOf(1));
		insuranceObj.setPurchasedQuantity(Long.valueOf(1));
		insuranceObj.setPrice(120.00);
		productMap.put(insuranceObj.getProductName().toLowerCase(), insuranceObj);

		ProductItemDetail electronicAppObj = new ProductItemDetail();
		electronicAppObj.setProductId(Long.valueOf(1));
		electronicAppObj.setProductCategory("Electronics");
		electronicAppObj.setProductName("Microwave Oven");
		electronicAppObj.setPerUnitQty(Long.valueOf(1));
		electronicAppObj.setPurchasedQuantity(Long.valueOf(1));
		electronicAppObj.setPrice(120.00);
		productMap.put(electronicAppObj.getProductName().toLowerCase(), electronicAppObj);

		rulesExecutorMock.execute(productMap);

		ProductItemDetail updatedProductItemDetail = productMap.get("Insurance per Electric Appliance".toLowerCase());
		//assertEquals(1, result);
		assertNotNull(updatedProductItemDetail);
		assertEquals(Double.valueOf(0.0), updatedProductItemDetail.getVat());
		assertEquals(Double.valueOf(20.0), updatedProductItemDetail.getDiscount());

	}
}
