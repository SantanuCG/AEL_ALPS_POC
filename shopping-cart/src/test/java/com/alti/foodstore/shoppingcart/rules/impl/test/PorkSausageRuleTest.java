package com.alti.foodstore.shoppingcart.rules.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.alti.foodstore.shoppingcart.entities.Product;
import com.alti.foodstore.shoppingcart.entities.ProductCategory;
import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.repository.IProductRepository;
import com.alti.foodstore.shoppingcart.rules.IRule;
import com.alti.foodstore.shoppingcart.rules.executor.impl.RulesExecutor;
import com.alti.foodstore.shoppingcart.rules.impl.BakedBeanRule;
import com.alti.foodstore.shoppingcart.rules.impl.BreadLoafRule;
import com.alti.foodstore.shoppingcart.rules.impl.ElectronicApplianceRule;
import com.alti.foodstore.shoppingcart.rules.impl.InsuranceRule;
import com.alti.foodstore.shoppingcart.rules.impl.ParacetamolRule;
import com.alti.foodstore.shoppingcart.rules.impl.PorkSausageRule;
import com.alti.foodstore.shoppingcart.rules.impl.VATRule;

public class PorkSausageRuleTest {

	@InjectMocks
	RulesExecutor rulesExecutorMock;

	@Mock
	IProductRepository productRepositoryMock;

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
	public void testPorkSausageRule() {
		Map<String, ProductItemDetail> productMap = new HashMap<>();
		ProductItemDetail productItemDetail = new ProductItemDetail();
		productItemDetail.setProductId(Long.valueOf(1));
		productItemDetail.setProductCategory("Food");
		productItemDetail.setProductName("Pork Sausages");
		productItemDetail.setPerUnitQty(Long.valueOf(1));
		productItemDetail.setPurchasedQuantity(Long.valueOf(1));
		productItemDetail.setPrice(6.00);
		productMap.put(productItemDetail.getProductName().toLowerCase(), productItemDetail);

		Product bakedBeanProduct = new Product();
		bakedBeanProduct.setProductId(Long.valueOf(1));
		ProductCategory prodCat = new ProductCategory();
		prodCat.setCategoryId(Long.valueOf(1));
		prodCat.setCategoryName("Food");
		bakedBeanProduct.setProductCategory(prodCat);
		bakedBeanProduct.setProductName("Baked Beans");
		bakedBeanProduct.setPerUnitQty(Long.valueOf(1));
		productItemDetail.setPrice(3.00);

		Mockito.when(productRepositoryMock.findByProductName("Baked Beans")).thenReturn(bakedBeanProduct);

		rulesExecutorMock.execute(productMap);

		ProductItemDetail freeBakedBeanProduct = productMap.get("Baked Beans".toLowerCase());
		assertNotNull(freeBakedBeanProduct);
		assertEquals(1, freeBakedBeanProduct.getFreeQuantity());

	}

}
