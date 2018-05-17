package com.alti.foodstore.shoppingcart.rules.executor.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alti.foodstore.shoppingcart.model.ProductItemDetail;
import com.alti.foodstore.shoppingcart.rules.IRule;
import com.alti.foodstore.shoppingcart.rules.executor.IRulesExecutor;

@Component
public class RulesExecutor implements IRulesExecutor{
	
	protected List<IRule> rulesList;
	
	public static final Logger logger = LoggerFactory.getLogger(RulesExecutor.class);
	
	@Autowired
	public RulesExecutor(List<IRule> rulesList) {
		this.rulesList = rulesList;
	}
	
	
	@Override
	public void execute(Map<String, ProductItemDetail> productDetailMap) {
		for(IRule iRule:rulesList) {
			int flag = iRule.executeRule(productDetailMap);
			if(flag ==1) {
				logger.info(iRule.getRuleName(), " execution complete");
			}
		}
		
	}
	

}
