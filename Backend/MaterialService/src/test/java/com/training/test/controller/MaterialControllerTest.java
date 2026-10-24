package com.training.test.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.training.controller.MaterialController;
import com.training.service.MaterialService;



@WebMvcTest(MaterialController.class)
public class MaterialControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(MaterialControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MaterialService materialServiceMock;
	
	@Test
	public void indexMaterialControllerTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
				.andExpect(content().string("Welcome to Spring Boot Material Service API!"));
	}	

	/*
	 * 
	 * Uncomment the below test for testing the '/material/controller/getMaterialCategories' mapping and it should fail
	 * After implementing the handler method in MaterialController execute the test again
	 * The below test case should pass once the MaterialController handler method is implemented properly
	 * 
	 * */
	
/*
	@Test
	public void getMaterialCategoriesTest() throws Exception {

		List<MaterialCategoryBean> materialCategoryBeans = new ArrayList<>();
		
		MaterialCategoryBean bean = new MaterialCategoryBean("C001", "Thread");
		materialCategoryBeans.add(bean);

		bean = new MaterialCategoryBean("C002", "Cloth");
		materialCategoryBeans.add(bean);		

		bean = new MaterialCategoryBean("C003", "Button");
		materialCategoryBeans.add(bean);		

		when(materialServiceMock.getMaterialCategories()).thenReturn(materialCategoryBeans);		
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/material/controller/getMaterialCategories"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$",hasSize(3)))

		//check if the json node exsits
        .andExpect(jsonPath("$[0].categoryId").exists())
        .andExpect(jsonPath("$[0].categoryName").exists())

        //check the type of json node
        .andExpect(jsonPath("$[0].categoryId").isString())
        .andExpect(jsonPath("$[0].categoryName").isString())
        //check the return value
        .andExpect(jsonPath("$[0].categoryId").value("C001"))
        .andExpect(jsonPath("$[0].categoryName").value("Thread"));
		
	}
*/	
	

	/*
	 * 
	 * Uncomment the below test for testing the '/material/controller/getMaterialCategoryById/{categoryId}' mapping and it should fail
	 * After implementing the handler method in MaterialController execute the test again
	 * The below test case should pass once the MaterialController handler method is implemented properly
	 * 
	 * */	
	
/*	
	@Test
	public void getMaterialCategoryByIdTest() throws Exception {
		String categoryId = "C001";
		MaterialCategoryBean bean = new MaterialCategoryBean("C001", "Thread");
		
		when(materialServiceMock.getMaterialCategoryById(categoryId)).thenReturn(bean);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/material/controller/getMaterialCategoryById/"+categoryId))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

		//check if the json node exsits
        .andExpect(jsonPath("$.categoryId").exists())
        .andExpect(jsonPath("$.categoryName").exists())

        //check the type of json node
        .andExpect(jsonPath("$.categoryId").isString())
        .andExpect(jsonPath("$.categoryName").isString())		
        
        .andExpect(jsonPath("$.categoryId").value("C001"))
        .andExpect(jsonPath("$.categoryName").value("Thread"));
		
	}
*/	
	
}
