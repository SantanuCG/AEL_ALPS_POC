package com.alti.foodstore.shoppingcart.service;

import java.util.List;

import com.alti.foodstore.shoppingcart.entities.Product;
import com.alti.foodstore.shoppingcart.model.OrderReceipt;
import com.alti.foodstore.shoppingcart.model.ProductItem;
import com.alti.foodstore.shoppingcart.model.ProductItemDetail;

public interface IProductService {

	public List<ProductItem> listAllProducts();
	
	public ProductItem saveProduct(ProductItem productDetail);
	
	public boolean deleteProduct(String productName);
	
	public OrderReceipt getOrderedProductReceipt(List<String> prodcutsOrdered);
}
