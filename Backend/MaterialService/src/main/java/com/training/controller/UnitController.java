package com.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.business.bean.UnitBean;
import com.training.service.UnitService;

@CrossOrigin("http://localhost:3003")
@RestController
public class UnitController {
	// TODO: Autowire UnitService object
	//Your code goes here
    @Autowired
    private UnitService unitService;
	/* 
	 * TODO:
	 * Method - getUnitDetails();
	 * Fetch all the units from UnitService and store it inside a List
	 * Return a ResponseEntity object passing the list of units 
	 */	
	@GetMapping(value="unit/controller/getUnitDetails",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UnitBean>> getUnitDetails(){
		
		//Your code goes here
		 List<UnitBean> list = unitService.getUnits();

	        return new ResponseEntity<List<UnitBean>>(
	                list, HttpStatus.OK);
	}
	
	/* 
	 * TODO:
	 * Method - getUnitsByCategoryId();
	 * Fetch all the units from UnitService by passing --> categoryId and store it inside a List
	 * Return a ResponseEntity object passing the list of units 
	 */	
	@GetMapping(value="unit/controller/getUnitsByCategoryId/{categoryId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UnitBean>> getUnitsByCategoryId(@PathVariable("categoryId") String categoryId){
		  List<UnitBean> list =
	                unitService.getUnitsBasedOnCategoryId(categoryId);

	        return new ResponseEntity<List<UnitBean>>(
	                list, HttpStatus.OK);
	}	

}
