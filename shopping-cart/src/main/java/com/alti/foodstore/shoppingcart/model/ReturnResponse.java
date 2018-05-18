package com.alti.foodstore.shoppingcart.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "RETURN_CODE", "RETURN_DESCRIPTION", "data"})
@Component
public class ReturnResponse {
	
		/** The return code. */
		@JsonProperty(value="RETURN_CODE")
		private String returnCode;
		
		/** The return description. */
		@JsonProperty(value="RETURN_DESCRIPTION")
		private String returnDescription;
		
		/** The data. */
		@JsonProperty(value="DATA")
		private Object data;
		
		/**
		 * Gets the return code.
		 *
		 * @return the return code
		 */
		public String getReturnCode() {
			return returnCode;
		}
		
		/**
		 * Sets the return code.
		 *
		 * @param returnCode the new return code
		 */
		public void setReturnCode(String returnCode) {
			this.returnCode = returnCode;
		}
		
		/**
		 * Gets the return description.
		 *
		 * @return the return description
		 */
		public String getReturnDescription() {
			return returnDescription;
		}
		
		/**
		 * Sets the return description.
		 *
		 * @param returnDescription the new return description
		 */
		public void setReturnDescription(String returnDescription) {
			this.returnDescription = returnDescription;
		}
		
		/**
		 * Gets the data.
		 *
		 * @return the data
		 */
		public Object getData() {
			return data;
		}
		
		/**
		 * Sets the data.
		 *
		 * @param data the new data
		 */
		public void setData(Object data) {
			this.data = data;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "SessionAlive [returnCode=" + returnCode
					+ ", returnDescription=" + returnDescription;
		}
		
	

}
