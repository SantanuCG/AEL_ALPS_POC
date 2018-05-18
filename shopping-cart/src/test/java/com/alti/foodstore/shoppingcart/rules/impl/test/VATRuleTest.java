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
public class VATRuleTest {

	@InjectMocks
	RulesExecutor rulesExecutorMock;

	@Before
	public void setupMock() {
		List<IRule> ruleList = new ArrayList<>();
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
	public void testVATRule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail porkSausageProd = new ProductItemDetail();
		porkSausageProd.setProductId(Long.valueOf(1));
		porkSausageProd.setProductCategory("Food");
		porkSausageProd.setProductName("Baked Beans");
		porkSausageProd.setPerUnitQty(Long.valueOf(1));
		porkSausageProd.setPurchasedQuantity(Long.valueOf(1));
		porkSausageProd.setPrice(3.00);
		productMap.put(porkSausageProd.getProductName().toLowerCase(), porkSausageProd);

		ProductItemDetail insuranceProd = new ProductItemDetail();
		insuranceProd.setProductId(Long.valueOf(2));
		insuranceProd.setProductCategory("Insurance");
		insuranceProd.setProductName("Insurance per Electric Appliance");
		insuranceProd.setPerUnitQty(Long.valueOf(1));
		insuranceProd.setPurchasedQuantity(Long.valueOf(1));
		insuranceProd.setPrice(120.00);
		productMap.put(insuranceProd.getProductName().toLowerCase(), insuranceProd);

		rulesExecutorMock.execute(productMap);

		ProductItemDetail porkSausageObj = productMap.get("Baked Beans".toLowerCase());
		assertNotNull(porkSausageObj);
		assertEquals(Double.valueOf(14.00), porkSausageObj.getVat());

		ProductItemDetail insuranceProdObj = productMap.get("Insurance per Electric Appliance".toLowerCase());
		assertNotNull(insuranceProdObj);
		assertEquals(Double.valueOf(0.0), insuranceProdObj.getVat());

	}

}
