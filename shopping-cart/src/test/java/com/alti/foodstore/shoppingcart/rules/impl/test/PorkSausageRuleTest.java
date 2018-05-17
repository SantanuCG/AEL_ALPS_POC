package com.alti.foodstore.shoppingcart.rules.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
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
import com.alti.foodstore.shoppingcart.rules.impl.PorkSausageRule;

public class PorkSausageRuleTest {
	
	@InjectMocks
	PorkSausageRule porkSausageRule;
	
	@Mock
	IProductRepository productRepositoryMock;
	
    @Before
	public void setupMock() {
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
		Double price = productItemDetail.getPrice() / productItemDetail.getPerUnitQty() * productItemDetail.getPurchasedQuantity();
		productItemDetail.setPrice(price);
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
		int result = porkSausageRule.executeRule(productMap);
		
		ProductItemDetail freeBakedBeanProduct = productMap.get("Baked Beans".toLowerCase());
		assertNotNull(freeBakedBeanProduct);
		assertEquals(1,result);
		assertEquals(1,freeBakedBeanProduct.getFreeQuantity());
		
		
		
	}

}
