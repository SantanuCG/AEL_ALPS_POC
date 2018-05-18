package com.alti.foodstore.shoppingcart.util;

import org.springframework.http.HttpStatus;

import com.alti.foodstore.shoppingcart.model.ReturnResponse;

public class CommonUtility {
	
	/**
	 * Gets the response.
	 *
	 * @param returnCode the return code
	 * @param returnDesc the return desc
	 * @param data the data
	 * @return the response
	 */
	public static ReturnResponse getResponse(HttpStatus returnCode, String returnDesc, Object data) {
		ReturnResponse returnMessage = new ReturnResponse();
		returnMessage.setData(data);
		returnMessage.setReturnCode(returnCode.toString());
		returnMessage.setReturnDescription(returnDesc);
		return returnMessage;
	}

}
