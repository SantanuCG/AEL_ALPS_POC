package com.alti.foodstore.shoppingcart.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

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
import com.alti.foodstore.shoppingcart.model.ReturnResponse;
import com.alti.foodstore.shoppingcart.service.IProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ShoppingCartApplication.class)
@AutoConfigureMockMvc
public class ShoppingCartControllerIntegrationTest {
	
	/** The rest template. */
	@Autowired
	private TestRestTemplate restTemplate;

	
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

		ResponseEntity<ReturnResponse> responseEntity = restTemplate.exchange("/api/alti/foodstore/products",
				HttpMethod.GET, null, new ParameterizedTypeReference<ReturnResponse>() {
		});

		
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(HttpStatus.OK.toString(), responseEntity.getBody().getReturnCode());
		assertNotSame(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getReturnCode());
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
		
		ResponseEntity<ReturnResponse> responseEntity = restTemplate.exchange("/api/alti/foodstore/products", HttpMethod.POST, requestObj,
				ReturnResponse.class);


		assertNotNull(responseEntity.getBody());
		assertEquals(HttpStatus.OK.toString(), responseEntity.getBody().getReturnCode());
		assertNotSame(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getReturnCode());
	}
	
	/**
	 * Test delete Product.
	 */
	@Test
	public void testDeleteProductSuccess() {

		ResponseEntity<ReturnResponse> responseEntity = restTemplate.exchange("/api/alti/foodstore/products/TV 25 inch XamZung",
				HttpMethod.DELETE, null, new ParameterizedTypeReference<ReturnResponse>() {
		});

		//List<ProductItem> returnMessage= responseEntity.getBody();

		assertNotNull(responseEntity.getBody());
		assertEquals("Product is deleted",responseEntity.getBody().getReturnDescription());
		assertEquals(HttpStatus.OK.toString(), responseEntity.getBody().getReturnCode());
		assertNotSame(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getReturnCode());
	}
	
	/**
	 * Test delete Product.
	 */
	@Test
	public void testDeleteProductFailure() {

		ResponseEntity<ReturnResponse> responseEntity = restTemplate.exchange("/api/alti/foodstore/products/XYZ",
				HttpMethod.DELETE, null, new ParameterizedTypeReference<ReturnResponse>() {
		});

	
		assertNotNull(responseEntity.getBody());
		assertEquals("Invalid Product Name",responseEntity.getBody().getReturnDescription());
		assertEquals(HttpStatus.NOT_FOUND.toString(), responseEntity.getBody().getReturnCode());
		assertNotSame(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getReturnCode());
	}
	
	
	/**
	 * Test Order Products.
	 */
	@Test
	public void testOrderProductsSuccess() {

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
		
		ResponseEntity<ReturnResponse> responseEntity = restTemplate.exchange("/api/alti/foodstore/products/order", HttpMethod.POST, requestObj,
				ReturnResponse.class);

		
		assertNotNull(responseEntity.getBody());
		assertEquals(HttpStatus.OK.toString(), responseEntity.getBody().getReturnCode());
		assertNotSame(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getReturnCode());
		assertNotSame(HttpStatus.NOT_FOUND.toString(), responseEntity.getBody().getReturnCode());
	}
	
	
	@Test
	public void testOrderProductsFailure() {

		List<String> shoppingCartList = new ArrayList<>();
				
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> mediaTypeList = new ArrayList<MediaType>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(mediaTypeList);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<List<String>> requestObj = new HttpEntity<>(shoppingCartList, requestHeaders);
		
		ResponseEntity<ReturnResponse> responseEntity = restTemplate.exchange("/api/alti/foodstore/products/order", HttpMethod.POST, requestObj,
				ReturnResponse.class);

		assertNotNull(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND.toString(), responseEntity.getBody().getReturnCode());
		assertNotSame(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getReturnCode());
	
	}

}
