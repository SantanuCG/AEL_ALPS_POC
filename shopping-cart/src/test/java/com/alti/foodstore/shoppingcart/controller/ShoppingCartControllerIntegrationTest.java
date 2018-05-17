package com.alti.foodstore.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.alti.foodstore.shoppingcart.ShoppingCartApplication;
import com.alti.foodstore.shoppingcart.model.OrderReceipt;
import com.alti.foodstore.shoppingcart.model.ProductItem;
import com.alti.foodstore.shoppingcart.service.IProductService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ShoppingCartApplication.class)
@AutoConfigureMockMvc
public class ShoppingCartControllerIntegrationTest {
	
	/** The rest template. */
	@Autowired
	private TestRestTemplate restTemplate;

	/** The iProductService */
	@Mock
	private IProductService iProductService;

	/** The session. */
	@Mock
	private HttpSession session;

	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setup() throws Exception {

		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test get Product List.
	 */
	@Test
	public void testGetProductList() {

		ResponseEntity<List<ProductItem>> responseEntity = restTemplate.exchange("/api/alti/foodstore/products",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductItem>>() {
		});

		List<ProductItem> returnMessage= responseEntity.getBody();

		assertNotNull(returnMessage);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	/**
	 * Test save Product.
	 */
	@Test
	public void testSaveProduct() {

		ProductItem productItem = new ProductItem();
		productItem.setProductName("iPhone");
		productItem.setPrice(190.00);
		productItem.setPerUnitQty(1);
		productItem.setProductCategory("Electronics");
		
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> mediaTypeList = new ArrayList<MediaType>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(mediaTypeList);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ProductItem> requestObj = new HttpEntity<>(productItem, requestHeaders);
		
		ResponseEntity<ProductItem> responseEntity = restTemplate.exchange("/api/alti/foodstore/products", HttpMethod.POST, requestObj,
				ProductItem.class);

		ProductItem savedProduct= responseEntity.getBody();

		assertNotNull(savedProduct);
		assertTrue(savedProduct.getProductId()>0);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	/**
	 * Test delete Product.
	 */
	@Test
	public void testDeleteProduct() {

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/alti/foodstore/products/TV 25 inch XamZung",
				HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
		});

		//List<ProductItem> returnMessage= responseEntity.getBody();

		assertNotNull(responseEntity.getBody());
		assertEquals("TV 25 inch XamZung is deleted",responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	/**
	 * Test Order Products.
	 */
	@Test
	public void testOrderProducts() {

		List<String> shoppingCartList = new ArrayList<>();
		shoppingCartList.add("Paracetamol XYZ 20 Tablets");
		shoppingCartList.add("Paracetamol XYZ 10 Tablets");
		shoppingCartList.add("Pork Sausages");
		shoppingCartList.add("Loaf of Bread");
		shoppingCartList.add("Insurance per Electric Appliance");
		shoppingCartList.add("Paracetamol XYZ 20 Tablets");
		shoppingCartList.add("Loaf of Bread");
		shoppingCartList.add("Insurance per Electric Appliance");
		shoppingCartList.add("TV 25 inch XamZung");
		shoppingCartList.add("6 eggs pack");
		
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> mediaTypeList = new ArrayList<MediaType>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(mediaTypeList);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<List<String>> requestObj = new HttpEntity<>(shoppingCartList, requestHeaders);
		
		ResponseEntity<OrderReceipt> responseEntity = restTemplate.exchange("/api/alti/foodstore/products/order", HttpMethod.POST, requestObj,
				OrderReceipt.class);

		OrderReceipt orderReceipt= responseEntity.getBody();

		assertNotNull(orderReceipt);
		assertTrue(orderReceipt.getOrderDetailList().size()>0);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotSame(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	

}
