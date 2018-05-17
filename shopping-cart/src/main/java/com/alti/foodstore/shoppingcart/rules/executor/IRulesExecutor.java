package com.alti.foodstore.shoppingcart.rules.executor;

import java.util.Map;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;

public interface IRulesExecutor {
	
	public void execute(Map<String, ProductItemDetail> productDetailMap);

}
