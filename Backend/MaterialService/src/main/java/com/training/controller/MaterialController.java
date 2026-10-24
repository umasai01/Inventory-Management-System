package com.training.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.training.business.bean.MaterialCategoryBean;
import com.training.service.MaterialService;

@CrossOrigin("http://localhost:3003")
@RestController
public class MaterialController {

	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	
	/*
	 * TODO: Autowire the MaterialService object
	 * 
	 */
	@Autowired
    private MaterialService materialService;
		
	@GetMapping(value="/")
	public String index() {
		return "Welcome to Spring Boot Material Service API!";
	}
	
	
	/* 
	 * TODO:
	 * Method - getMaterialCategories()
	 * Fetch all the material categories details using MaterialService object and store it inside a List
	 * Return a ResponseEntity object passing the list of material categories
	 */
		
	@GetMapping(value = "/material/controller/getMaterialCategories", 
		 produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MaterialCategoryBean>> getMaterialCategories() {
		//Your code goes here
	     List<MaterialCategoryBean> list =
	                materialService.getMaterialCategories();

	        return new ResponseEntity<List<MaterialCategoryBean>>(
	                list, HttpStatus.OK);
	}

	
	/*
	 * TODO: 
	 * Method - getMaterialCategoryById()
	 * Fetch a single MaterialCategoryBean using MaterialService object by passing --> categoryId
	 * Return a ResponseEntity object passing the MaterialCategoryBean object 
	 */
			
	@GetMapping(value = "/material/controller/getMaterialCategoryById/{categoryId}", 
			 produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MaterialCategoryBean> getMaterialCategoryById(@PathVariable String categoryId) {
		//Your code goes here
		  MaterialCategoryBean materialCategoryBean =
	                materialService.getMaterialCategoryById(categoryId);

	        return new ResponseEntity<MaterialCategoryBean>(
	                materialCategoryBean, HttpStatus.OK);
	}
	
	
}
