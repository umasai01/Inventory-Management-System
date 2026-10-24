package com.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import com.training.business.bean.VendorBean;
import com.training.service.VendorService;

@CrossOrigin(origins = {"http://localhost:3003"})
@RestController
public class VendorController {

	
	
	/*
	 * Autowire the VendorService object
	 * 
	 * */
	//Your code goes here
	
	private final VendorService vendorService;
	
	@Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
	
	
	@GetMapping("/")
	public String index() {
		return "Welcome to Spring Boot Vendor Service API!";
	}

	
	/* 
	 * Method - getVendorDetails()
	 * Fetch all the vendor details using VendorService and store it inside a List
	 * Return a ResponseEntity object passing the list of vendor details
	 * 
	 */
	
	
	@GetMapping("/vendor/controller/getVendors")
	public ResponseEntity<List<VendorBean>> getVendorDetails() {
		//Your code goes here
		List<VendorBean> list = vendorService.getVendorDetails();
		return ResponseEntity.ok(list);
	}
}