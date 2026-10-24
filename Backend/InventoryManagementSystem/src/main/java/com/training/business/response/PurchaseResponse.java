package com.training.business.response;

import com.training.business.bean.PurchaseBean;

// Response wrapper for add purchase API: sends operation status, message, and saved purchase.
    public class PurchaseResponse {
        private String status;
        private String message;
		private PurchaseBean purchase;

		public PurchaseResponse(String status, String message, PurchaseBean purchase) {
            this.status = status;
            this.message = message;
			this.purchase = purchase;
        }

		// Getter used in JSON response serialization.
		public String getStatus() { return status; }
		// Getter used in JSON response serialization.
		public String getMessage() { return message; }
		// Getter used in JSON response serialization.
		public PurchaseBean getPurchase() { return purchase; }
    }
