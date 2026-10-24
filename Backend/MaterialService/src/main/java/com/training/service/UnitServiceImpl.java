package com.training.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.business.bean.UnitBean;
import com.training.dao.UnitDAO;
import com.training.entity.UnitEntity;

@Service
public class UnitServiceImpl implements UnitService{	
	
	/*
	 * POA - 7:
	 * Autowire UnitDAO object
	 * 
	 */
	 @Autowired
	    private UnitDAO unitDAO;
	//Your code goes here

	/*
	* POA - 8:
	* TODO:
	* Method getUnitsBasedOnCategoryId()
	* retrieve all the units by passing the categoryId
	* check if the list is not null
	* convert the list of entities to bean 
	* set categoryId to unit and return the list
	*/
	public List<UnitBean> getUnitsBasedOnCategoryId(String categoryId) {
		//Your code goes here
		 List<UnitBean> unitBeans = new ArrayList<>();

	        List<UnitEntity> unitEntities =
	                unitDAO.findByMaterialCategoryEntityCategoryId(categoryId);

	        if (unitEntities != null && !unitEntities.isEmpty()) {

	            for (UnitEntity entity : unitEntities) {

	                UnitBean unitBean = new UnitBean();

	                BeanUtils.copyProperties(entity, unitBean);

	                // Set categoryId from category relation
	                if (entity.getMaterialCategoryEntity() != null) {
	                    unitBean.setCategoryId(
	                            entity.getMaterialCategoryEntity().getCategoryId()
	                    );
	                }

	                unitBeans.add(unitBean);
	            }
	        }

		return unitBeans;
	}

	/*
	* POA - 9:
	* TODO:
	* Method getUnitsBasedOnCategoryId()
	* retrieve all the units 
	* check if the list is not null
	* convert the list of entities to bean 
	* set categoryId to unit and return the list
	*/
	@Override
	public List<UnitBean> getUnits() {
		 List<UnitBean> unitBeans = new ArrayList<>();

	        List<UnitEntity> unitEntities =
	                unitDAO.findAll();

	        if (unitEntities != null && !unitEntities.isEmpty()) {

	            for (UnitEntity entity : unitEntities) {

	                UnitBean unitBean = new UnitBean();

	                BeanUtils.copyProperties(entity, unitBean);

	                // Set categoryId from category relation
	                if (entity.getMaterialCategoryEntity() != null) {
	                    unitBean.setCategoryId(
	                            entity.getMaterialCategoryEntity().getCategoryId()
	                    );
	                }

	                unitBeans.add(unitBean);
	            }
	        }
		return unitBeans;
	}
	
}
