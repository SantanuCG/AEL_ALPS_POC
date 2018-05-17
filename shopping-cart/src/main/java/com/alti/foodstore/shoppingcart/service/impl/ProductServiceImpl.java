package com.alti.foodstore.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alti.foodstore.shoppingcart.entities.Product;
import com.alti.foodstore.shoppingcart.entities.ProductCategory;
import com.alti.foodstore.shoppingcart.model.OrderReceipt;
import com.alti.foodstore.shoppingcart.model.ProductItem;
import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.repository.IProductCategoryRepository;
import com.alti.foodstore.shoppingcart.repository.IProductRepository;
import com.alti.foodstore.shoppingcart.rules.executor.IRulesExecutor;
import com.alti.foodstore.shoppingcart.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	IProductRepository productRepository;

	@Autowired
	IProductCategoryRepository productCategoryRepository;

	@Autowired
	IRulesExecutor iRulesExecutor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alti.foodstore.shoppingcart.service.IProductService#listAllProducts()
	 */
	@Override
	@Transactional
	public List<ProductItem> listAllProducts() {
		List<Product> productList = productRepository.findAll();

		List<ProductItem> productDetailsList = new ArrayList<>();

		for (Product product : productList) {
			ProductItem productDetail = new ProductItem();
			productDetail.setProductId(product.getProductId());
			productDetail.setProductName(product.getProductName());
			productDetail.setPrice(product.getPrice());
			productDetail.setPerUnitQty(product.getPerUnitQty());
			productDetail.setProductCategory(product.getProductCategory().getCategoryName());
			productDetailsList.add(productDetail);
		}

		return productDetailsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alti.foodstore.shoppingcart.service.IProductService#deleteProduct(java.
	 * lang.String)
	 */
	@Override
	@Transactional
	public boolean deleteProduct(String productName) {
		boolean flag = false;
		Product product = productRepository.findByProductName(productName);

		if (product != null) {
			productRepository.delete(product);
			flag = true;
		}

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alti.foodstore.shoppingcart.service.IProductService#saveProduct(com.alti.
	 * foodstore.shoppingcart.model.ProductItem)
	 */
	@Override
	@Transactional
	public ProductItem saveProduct(ProductItem productItem) {

		if (productItem != null) {
			ProductCategory productCategory = productCategoryRepository
					.findByCategoryName(productItem.getProductCategory());

			if (productCategory != null
					&& (productRepository.findByProductName(productItem.getProductName().trim()) == null)) {
				Product product = new Product();
				product.setProductName(productItem.getProductName().trim());
				product.setPrice(productItem.getPrice());
				product.setPerUnitQty(productItem.getPerUnitQty());
				product.setProductCategory(productCategory);

				productRepository.save(product);

				Product insertedProduct = productRepository.findByProductName(productItem.getProductName());

				if (insertedProduct != null) {
					productItem.setProductId(insertedProduct.getProductId());
				}

			}

		}

		return productItem;

	}

	/*
	 * Create consolidated ProductDetail Map from input product list
	 * */
	private Map<String, ProductItemDetail> getConsolidateProductOrderList(List<String> prodcutsOrdered) {

		List<Product> productList = productRepository.findAll();

		Map<String, ProductItemDetail> productMap = new HashMap<>();

		for (String productName : prodcutsOrdered) {

			Product product = findProduct(productName, productList);

			if (product != null) {
				if (productMap.get(productName.toLowerCase()) == null) {
					ProductItemDetail productItemDetail = new ProductItemDetail();
					productItemDetail.setProductId(product.getProductId());
					productItemDetail.setProductCategory(product.getProductCategory().getCategoryName());
					productItemDetail.setProductName(product.getProductName());
					productItemDetail.setPerUnitQty(product.getPerUnitQty());
					productItemDetail.setPurchasedQuantity(product.getPerUnitQty());
					Double price = product.getPrice() / product.getPerUnitQty() * productItemDetail.getPurchasedQuantity();
					productItemDetail.setPrice(price);

					productMap.put(productName.toLowerCase(), productItemDetail);
				} else {
					ProductItemDetail productItemDetail = (ProductItemDetail) productMap.get(productName.toLowerCase());
					long purchasedQuantity = productItemDetail.getPurchasedQuantity() + product.getPerUnitQty();
					productItemDetail.setPurchasedQuantity(purchasedQuantity);
					productItemDetail.setTotalQuantity(purchasedQuantity);
					Double price = product.getPrice() / product.getPerUnitQty() * productItemDetail.getPurchasedQuantity();
					productItemDetail.setPrice(price);

					productMap.replace(productName.toLowerCase(), productItemDetail);
				}
			}

		}

		return productMap;
	}

	@Override
	public OrderReceipt getOrderedProductReceipt(List<String> prodcutsOrdered) {

		List<ProductItemDetail> finalProductItemDetailList = new ArrayList<>();
		OrderReceipt orderReceipt = new OrderReceipt();

		Map<String, ProductItemDetail> productDetailMap = getConsolidateProductOrderList(prodcutsOrdered);
		
		iRulesExecutor.execute(productDetailMap);

		for (Map.Entry<String, ProductItemDetail> entry : productDetailMap.entrySet()) {
			finalProductItemDetailList.add(entry.getValue());

		}

		orderReceipt.setOrderDetailList(finalProductItemDetailList);

		return orderReceipt;
	}

	/*
	 * Find product by name
	 */
	private Product findProduct(String productName, List<Product> productList) {
		Product product = null;

		for (Product prod : productList) {
			if (productName.equalsIgnoreCase(prod.getProductName())) {
				product = prod;
				break;
			}
		}

		return product;
	}

}
