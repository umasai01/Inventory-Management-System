package com.training.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.training.business.bean.PurchaseBean;
import com.training.entity.PurchaseEntity;

@Repository
public class PurchaseDataAccess {
	
	/*
	* POA - 12:
	* Autowire the PurchaseDAO to perform database operations.
	*/
	@Autowired
	private PurchaseDAO purchaseDAO;


	/*
	* POA - 13:
	* Declare a method savePurchaseDetail which accepts PurchaseBean as argument and returns the saved purchase details as PurchaseBean.
	* Inside the method, create an instance of PurchaseEntity and set the values from PurchaseBean to PurchaseEntity. 
	* Call the save method of purchaseDAO to save the purchase details and return the saved details as PurchaseBean.
	*
	*/
	public PurchaseBean savePurchaseDetail(PurchaseBean purchaseBean) throws Exception {

		    PurchaseEntity purchaseEntity = new PurchaseEntity();

		    purchaseEntity.setTransactionId(purchaseBean.getTransactionId());
		    purchaseEntity.setVendorName(purchaseBean.getVendorName());
		    purchaseEntity.setMaterialCategoryId(purchaseBean.getMaterialCategoryId());
		    purchaseEntity.setMaterialTypeId(purchaseBean.getMaterialTypeId());
		    purchaseEntity.setBrandName(purchaseBean.getBrandName());
		    purchaseEntity.setUnitId(purchaseBean.getUnitId());
		    purchaseEntity.setQuantity(purchaseBean.getQuantity());
		    purchaseEntity.setPurchaseAmount(purchaseBean.getPurchaseAmount());
		    purchaseEntity.setPurchaseDate(purchaseBean.getPurchaseDate());
		    purchaseEntity.setStatus(purchaseBean.getStatus());

		    PurchaseEntity savedEntity = purchaseDAO.save(purchaseEntity);

		    PurchaseBean savedPurchaseBean = new PurchaseBean();

		    savedPurchaseBean.setPurchaseId(savedEntity.getPurchaseId());
		    savedPurchaseBean.setTransactionId(savedEntity.getTransactionId());
		    savedPurchaseBean.setVendorName(savedEntity.getVendorName());
		    savedPurchaseBean.setMaterialCategoryId(savedEntity.getMaterialCategoryId());
		    savedPurchaseBean.setMaterialTypeId(savedEntity.getMaterialTypeId());
		    savedPurchaseBean.setBrandName(savedEntity.getBrandName());
		    savedPurchaseBean.setUnitId(savedEntity.getUnitId());
		    savedPurchaseBean.setQuantity(savedEntity.getQuantity());
		    savedPurchaseBean.setPurchaseAmount(savedEntity.getPurchaseAmount());
		    savedPurchaseBean.setPurchaseDate(savedEntity.getPurchaseDate());
		    savedPurchaseBean.setStatus("SUCCESS");

		    return savedPurchaseBean;
	}
}