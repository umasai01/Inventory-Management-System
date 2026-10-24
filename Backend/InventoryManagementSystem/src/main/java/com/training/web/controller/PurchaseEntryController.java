package com.training.web.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.training.business.bean.MaterialCategoryBean;
import com.training.business.bean.MaterialTypeBean;
import com.training.business.bean.PurchaseBean;
import com.training.business.bean.UnitBean;
import com.training.business.bean.VendorBean;
import com.training.business.response.PurchaseResponse;
import com.training.business.response.UnitAndTypeResponse;
import com.training.exceptions.MicroServiceException;
import com.training.service.PurchaseService;
import com.training.web.client.MaterialCategoryConsumer;
import com.training.web.client.MaterialTypeConsumer;
import com.training.web.client.UnitServiceConsumer;
import com.training.web.client.VendorServiceConsumer;

/**
 * <br/>
 * CLASS DESCRIPTION: <br/>
 * A controller class for receiving and handling all material purchase related
 * transactions from the User Interface. <br/>
 *
 */
@CrossOrigin(origins = "http://localhost:3003")
@RestController
@SessionAttributes({ "purchaseBean" })
public class PurchaseEntryController {

    private static Logger LOGGER = LoggerFactory.getLogger(PurchaseEntryController.class);

	

    @Autowired
    private PurchaseService purchaseService;

	
    @Autowired
    private VendorServiceConsumer vendorServiceConsumer;
	
    @Autowired
    private MaterialCategoryConsumer materialCategoryConsumer;
	
    @Autowired
    private UnitServiceConsumer unitServiceConsumer;
	
    @Autowired
    private MaterialTypeConsumer materialTypeConsumer;
	/**
	 * METHOD DESCRIPTION: <br/>
	 * This method sets PurchaseBean into the model attribute and redirects to
	 * PurchaseEntry.jsp.
	 * 
	 * @return
	 * @throws Exception
	 */
    @GetMapping("purchaseEntry")
	public ResponseEntity<PurchaseBean> purchaseEntry() throws Exception {
		return ResponseEntity.ok(new PurchaseBean());
    }



	/**
	 * METHOD DESCRIPTION: <br/>
	 * This method returns the vendor list to be populated on the
	 * PurchasEntry.jsp. getVendorBeanList method of VendorServiceConsumer is
	 * called to get the vendor list.
	 * 
	 * @return vendorList - List of vendor values
	 * @throws MicroServiceException
	 */

    @GetMapping("vendors")
	public ResponseEntity<List<VendorBean>> generateVendorList() throws MicroServiceException {
	
    	 List<VendorBean> vendorList =
                 vendorServiceConsumer.getVendorBeanList();

         if (vendorList == null) {
             vendorList = List.of();//return empty list
         }

         return ResponseEntity.ok(vendorList);
	}
 
	/**
	 * METHOD DESCRIPTION: <br/>
	 * This method returns the material unit and type list to be populated in
	 * PurchaseEntry.jsp for chosen material category. hitGetUnitsByCategoryId
	 * method of UnitServiceConsumer class to be called to get the list of
	 * material unit. hitGetTypesBasedOnCategoryId method of
	 * MaterialTypeConsumer class to be called to get the list of material type.
	 * 
	 * @param purchaseBean
	 * @param HttpSession
	 * @return ModelAndView
	 * @throws MicroServiceException
	 */
    @PostMapping("getUnitAndTypeList")
	public ResponseEntity<UnitAndTypeResponse> generateUnitAndTypeList(@RequestBody PurchaseBean purchaseBean,
													   HttpSession session) throws MicroServiceException {
	   	  String categoryId = purchaseBean.getMaterialCategoryId();

          List<UnitBean> unitList =
                  unitServiceConsumer.hitGetUnitsByCategoryId(categoryId);

          List<MaterialTypeBean> materialTypeList =
                  materialTypeConsumer.hitGetTypesBasedOnCategoryId(categoryId);

          if (unitList == null) {
              unitList = List.of();
          }

          if (materialTypeList == null) {
              materialTypeList = List.of();
          }

          session.setAttribute("purchaseBean", purchaseBean);

          return ResponseEntity.ok(new UnitAndTypeResponse(unitList, materialTypeList));											
	}

	/**
	 * METHOD DESCRIPTION: <br/>
	 * This method returns the material category list to be populated on the
	 * PurchasEntry.jsp. getMaterialCategoryBeanList method of
	 * MaterialCategoryConsumer is called to get the material category list.
	 * 
	 * @return List - MaterialCategoryBean
	 * @throws MicroServiceException
	 */
    @GetMapping("categories")
	public ResponseEntity<List<MaterialCategoryBean>> generateCategoryList() throws MicroServiceException {
	
		// Code goes here
    	  List<MaterialCategoryBean> categoryList =
                  materialCategoryConsumer.getMaterialCategoryBeanList();

          if (categoryList == null) {
              categoryList = List.of();
          }

          return ResponseEntity.ok(categoryList);
	}
	
	/**
	 * METHOD DESCRIPTION: <br/>
	 * This method is used to insert purchase details filled on
	 * PurchaseEntry.jsp in to the purchase and payment table. Upon successful
	 * insert redirects to PurchaseSuccess.jsp
	 * 
	 * @param purchaseBean
	 * @param BindingResult
	 * @param ModelMap
	 * @param HttpSession
	 * @return ModelAndView
	 * @throws Exception
	 */
    @PostMapping("addPurchaseDetail")
	public ResponseEntity<PurchaseResponse> addPurchaseDetail(@RequestBody @Valid PurchaseBean purchaseBean,
											  HttpSession session) throws Exception {
		LOGGER.info("Execution Started [addPurchaseDetail]");
		
		PurchaseBean savedPurchaseBean =
	            purchaseService.addPurchaseDetails(purchaseBean);

	    session.setAttribute("purchaseBean", savedPurchaseBean);

	    LOGGER.info("Execution Completed [addPurchaseDetail]");

	    return ResponseEntity.ok(
	            new PurchaseResponse(
	                    "SUCCESS",
	                    "Purchase details added successfully",
	                    savedPurchaseBean
	            )
	    );									
	}
}