package com.alti.foodstore.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

		List<ProductItem> productDetailsList = productList.stream()
				.map(p-> new ProductItem(p.getProductId(), p.getProductName(),p.getPerUnitQty(), 
						p.getPrice(),p.getProductCategory().getCategoryName())).collect(Collectors.toList());
			


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

			}else {
				productItem.setProductId(Long.valueOf(0));
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

		prodcutsOrdered.forEach(productName -> {
						Optional<Product> prod = findProduct(productName, productList);
						if(prod.isPresent()) 
						{
							Product product = prod.get();
							
							ProductItemDetail productItemDetail = productMap.entrySet().stream()
											.filter(e -> e.getKey().equals(productName.toLowerCase()))
											.map(Map.Entry::getValue)
											.findFirst() 
									 		.orElse(null);
							
							if(productItemDetail==null) {
								productItemDetail = new ProductItemDetail();
								productItemDetail.setProductId(product.getProductId());
								productItemDetail.setProductCategory(product.getProductCategory().getCategoryName());
								productItemDetail.setProductName(product.getProductName());
								productItemDetail.setPerUnitQty(product.getPerUnitQty());
								productItemDetail.setPurchasedQuantity(product.getPerUnitQty());
								productItemDetail.setPrice(product.getPrice());
								productItemDetail.setConsideredInTotalAmount(true);
							}else {
								long purchasedQuantity = productItemDetail.getPurchasedQuantity() + product.getPerUnitQty();
								productItemDetail.setPurchasedQuantity(purchasedQuantity);
								productItemDetail.setTotalQuantity(purchasedQuantity);
																
							}
							
							productMap.put(productName.toLowerCase(), productItemDetail);
						}
		});

		return productMap;
	}

	@Override
	public OrderReceipt getOrderedProductReceipt(List<String> prodcutsOrdered) {

		OrderReceipt orderReceipt = new OrderReceipt();
		Map<String, ProductItemDetail> productDetailMap = getConsolidateProductOrderList(prodcutsOrdered);
		
		iRulesExecutor.execute(productDetailMap);
		
		List<ProductItemDetail> finalProductItemDetailList = new ArrayList<>(productDetailMap.values());
		orderReceipt.setOrderDetailList(finalProductItemDetailList);

		return orderReceipt;
	}

	/*
	 * Find product by name
	 */
	private Optional<Product> findProduct(String productName, List<Product> productList) {
			return productList.stream().filter(prod -> productName.equalsIgnoreCase(prod.getProductName())).findFirst();

	}

}
