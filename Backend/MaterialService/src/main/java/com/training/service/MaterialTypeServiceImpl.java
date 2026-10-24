package com.training.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.business.bean.MaterialTypeBean;
import com.training.dao.MaterialTypeDAO;
import com.training.entity.MaterialTypeEntity;
@Service
public class MaterialTypeServiceImpl implements MaterialTypeService{
	
	/*
	 * POA - 4:
	 * TODO: Autowire MaterialCategoryDAO object
	 * 
	 */
	//Your code goes here	
	@Autowired
	private MaterialTypeDAO materialTypeDAO;
	/*
	* POA - 5:
	* TODO:
	* Method getMaterialTypesBasedOnCategoryId()
	* retrieve all the material types by passing categoryId
	* check if the list is not null
	* convert the list of entities to bean 
	* set categoryId to materialtype and return the list
	*/

	@Override
	public List<MaterialTypeBean> getMaterialTypesBasedOnCategoryId(String categoryId) {	

        List<MaterialTypeBean> beans = new ArrayList<>();

        List<MaterialTypeEntity> entities =
                materialTypeDAO.findByMaterialCategoryEntityCategoryId(categoryId);

        if (entities != null && !entities.isEmpty()) {

            for (MaterialTypeEntity entity : entities) {

                MaterialTypeBean bean = new MaterialTypeBean();

                BeanUtils.copyProperties(entity, bean);

                // Set categoryId from the entity relation
                if (entity.getMaterialCategoryEntity() != null) {
                    bean.setCategoryId(
                            entity.getMaterialCategoryEntity().getCategoryId()
                    );
                }

                beans.add(bean);
            }
        }

		return beans;
	}

	/*
	 * POA - 6:
	 * TODO:
	 * Method getMaterialTypes()
	 * retrieve all the material types 
	 * check if the list is not null
	 * convert the list of entities to bean 
	 * set categoryId to materialtype and return the list
	*/

	@Override
	public List<MaterialTypeBean> getMaterialTypes() {
		//Your code goes here
		  List<MaterialTypeBean> beans = new ArrayList<>();

	        List<MaterialTypeEntity> entities =
	                materialTypeDAO.findAll();

	        if (entities != null && !entities.isEmpty()) {

	            for (MaterialTypeEntity entity : entities) {

	                MaterialTypeBean bean = new MaterialTypeBean();

	                BeanUtils.copyProperties(entity, bean);

	                if (entity.getMaterialCategoryEntity() != null) {
	                    bean.setCategoryId(
	                            entity.getMaterialCategoryEntity().getCategoryId()
	                    );
	                }

	                beans.add(bean);
	            }
	        }

	        return beans;
	}

	
}
