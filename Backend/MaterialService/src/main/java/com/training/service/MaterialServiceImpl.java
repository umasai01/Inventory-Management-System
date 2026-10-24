package com.training.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.business.bean.MaterialCategoryBean;
import com.training.dao.MaterialCategoryDAO;
import com.training.entity.MaterialCategoryEntity;

@Service
public class MaterialServiceImpl implements MaterialService {

	private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

	
	/*
	 * POA - 1:
	 *TODO: Autowire MaterialCategoryDAO object
	 * 
	 * */
	//Your code goes here
	@Autowired
	private MaterialCategoryDAO materialCategoryDAO;

	/*
	 * POA - 2:
	 * TODO: 
	 * Method - getMaterialCategoryById()
	 * Use MaterialCategoryDAO object findById method to fetch the entity by passing --> categoryId
	 * Check if the entity is present
	 * 		initialize the materialCategoryBean object
	 * 		copy the properties value from entity to materialCategoryBean object
	 * */
	
	@Override
	public MaterialCategoryBean getMaterialCategoryById(String categoryId) {
		MaterialCategoryBean materialCategoryBean = null;

	    MaterialCategoryEntity entity =
	            materialCategoryDAO.findById(categoryId).orElse(null);

	    if (entity != null) {
	        materialCategoryBean = new MaterialCategoryBean();

	      BeanUtils.copyProperties(entity, materialCategoryBean);
	    }

	    return materialCategoryBean;
	}

	
	/*
	 * POA - 3:
	 * Method - getMaterialCategories()
	 * Use the MaterialCategoryDAO to get all the MaterialCategoryEntity objects
	 * Check if list is not empty then 
	 * 		Declare a MaterialCategoryBean object with null value
	 * 		Loop through all the material categories
	 * 			Initialize a new MaterialCategoryBean object 
	 * 			Copy each property value of entity object to bean object
	 * 			Add the bean object to the materialCategoryBeans list
	 */

	
	@Override
	public List<MaterialCategoryBean> getMaterialCategories() {

	    List<MaterialCategoryBean> materialCategoryBeans =
	            new ArrayList<>();

	    List<MaterialCategoryEntity> materialCategoryEntities =
	            materialCategoryDAO.findAll();

	    if (materialCategoryEntities != null && !materialCategoryEntities.isEmpty()) {

	        for (MaterialCategoryEntity entity : materialCategoryEntities) {

	            MaterialCategoryBean materialCategoryBean = new MaterialCategoryBean();

	            BeanUtils.copyProperties(entity, materialCategoryBean);

	            materialCategoryBeans.add(materialCategoryBean);
	        }
	    }

	    return materialCategoryBeans;
	}

}
