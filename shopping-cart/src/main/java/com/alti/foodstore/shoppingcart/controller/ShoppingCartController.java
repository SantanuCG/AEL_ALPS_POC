package com.alti.foodstore.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alti.foodstore.shoppingcart.model.OrderReceipt;
import com.alti.foodstore.shoppingcart.model.ProductItem;
import com.alti.foodstore.shoppingcart.model.ReturnResponse;
import com.alti.foodstore.shoppingcart.service.IProductService;
import com.alti.foodstore.shoppingcart.util.CommonUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="Shopping Cart Rules POC")
@RestController
public class ShoppingCartController {
	
	@Autowired
	IProductService productService;
	
	
	@ApiOperation(value = "Returns List of Products available", notes = "Returns Product summary", response = ReturnResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucessful Retrieval of Appointments", response = ReturnResponse.class ) ,
	@ApiResponse(code = 404, message = "No data found"),
	@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/api/alti/foodstore/products")
	public ReturnResponse getProductList() {
		
		List<ProductItem> productDetailList = productService.listAllProducts();
		
		if(productDetailList.isEmpty()) {
			return CommonUtility.getResponse(HttpStatus.NOT_FOUND, "No data found", productDetailList);
		}
		
		return CommonUtility.getResponse(HttpStatus.OK, "Product list found", productDetailList); 
	}
	
	@ApiOperation(value = "Saves a new Product", notes = "New Product Information", response = ReturnResponse.class, consumes="ProductItem object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product data saved", response = ReturnResponse.class ) ,
	@ApiResponse(code = 404, message = "No data found"),
	@ApiResponse(code = 500, message = "Product Data Not Saved")})
	@PostMapping("/api/alti/foodstore/products")
	public ReturnResponse saveProduct(@RequestBody ProductItem productDetail) {
		
		ProductItem newProduct = productService.saveProduct(productDetail);
		
		if(newProduct==null || newProduct.getProductId()<=0) {
			return CommonUtility.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Product Data Not Saved", newProduct);
			
		}
		
		return CommonUtility.getResponse(HttpStatus.OK, "Product data saved", newProduct); 
			
	}
	
	@ApiOperation(value = "Deletes an existing product", notes = "Returns confirmation message", response = ReturnResponse.class, consumes="Product Name String")
	@ApiParam(name="productName",type="String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product is deleted", response = ReturnResponse.class ) ,
	@ApiResponse(code = 404, message = "Invalid Product Name"),
	@ApiResponse(code = 500, message = "Internal Server Error")})
	@DeleteMapping("/api/alti/foodstore/products/{productName}")
	public ReturnResponse deleteProduct(@PathVariable String productName) {
		boolean flag = false;
		
		flag = productService.deleteProduct(productName);
		if(!flag) {
			return CommonUtility.getResponse(HttpStatus.NOT_FOUND, "Invalid Product Name", productName);
		
		}
		
		return CommonUtility.getResponse(HttpStatus.OK, "Product is deleted", productName);
	
	}
	
	
	@ApiOperation(value = "Generates Receipt of Products Ordered", notes = "Returns Product Receipt", response = ReturnResponse.class, consumes="List of product names")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Product Order receipt generated", response = ReturnResponse.class ) ,
	@ApiResponse(code = 404, message = "No products ordered"),
	@ApiResponse(code = 500, message = "Order Receipt Could Not be generated")})
	@PostMapping("/api/alti/foodstore/products/order")
	public ReturnResponse orderProducts(@RequestBody List<String> prodcutsOrderedList) {
		
		if(prodcutsOrderedList.isEmpty()) {
			return CommonUtility.getResponse(HttpStatus.NOT_FOUND, "No products ordered", prodcutsOrderedList);
	
		}
		
		OrderReceipt orderReceipt = productService.getOrderedProductReceipt(prodcutsOrderedList);
		
		if(orderReceipt==null || orderReceipt.getOrderDetailList().isEmpty()) {
			return CommonUtility.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Order Receipt Could Not be generated", orderReceipt);
	
		}
		
		return CommonUtility.getResponse(HttpStatus.OK, "Product Order receipt generated", orderReceipt);
		
	}

}
