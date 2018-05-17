package com.alti.foodstore.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alti.foodstore.shoppingcart.model.OrderReceipt;
import com.alti.foodstore.shoppingcart.model.ProductItem;
import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.service.IProductService;
import com.alti.foodstore.shoppingcart.util.CustomErrorType;


@RestController
public class ShoppingCartController {
	
	@Autowired
	IProductService productService;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/api/alti/foodstore/products")
	public ResponseEntity<?> getProductList() {
		
		List<ProductItem> productDetailList = new ArrayList<>();
		productDetailList = productService.listAllProducts();
		
		if(productDetailList.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("No data found"),
                    HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ProductItem>>(productDetailList, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/api/alti/foodstore/products")
	public ResponseEntity<?> saveProduct(@RequestBody ProductItem productDetail) {
		
		ProductItem newProduct = productService.saveProduct(productDetail);
		
		if(newProduct!=null && newProduct.getProductId()>0) {
			return new ResponseEntity<ProductItem>(newProduct, HttpStatus.OK);
		}
		
		return new ResponseEntity(new CustomErrorType("Product could not be saved"),
                HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@DeleteMapping("/api/alti/foodstore/products/{productName}")
	public ResponseEntity<?> deleteProduct(@PathVariable String productName) {
		boolean flag = false;
		
		flag = productService.deleteProduct(productName);
		if(flag == false) {
			return new ResponseEntity(new CustomErrorType("Invalid product"),
	                HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(productName+" is deleted", HttpStatus.OK);
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/api/alti/foodstore/products/order")
	public ResponseEntity<?> orderProducts(@RequestBody List<String> prodcutsOrdered) {
		
		if(prodcutsOrdered.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("No products ordered"),
	                HttpStatus.NOT_FOUND);
		}
		
		OrderReceipt orderReceipt = productService.getOrderedProductReceipt(prodcutsOrdered);
		
		if(orderReceipt==null) {
			return new ResponseEntity(new CustomErrorType("Order Receipt Could Not be generated"),
	                HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<OrderReceipt>(orderReceipt, HttpStatus.OK);
	}

}
