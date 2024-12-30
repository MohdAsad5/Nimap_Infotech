package com.nimap.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimap.dto.CategoryDTO;
import com.nimap.entity.Category;
import com.nimap.repository.CategoryRepository;
import com.nimap.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired 
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@GetMapping("/getAllCategory")
    public ResponseEntity<Object> getAllCategories(@RequestParam(defaultValue = "0") int page) {
		Page<Category> pages = categoryService.getAllCategories(page);
		List<Map<String, Object>> categoryList = null;
		
		if(pages.getSize() > 0) {
			categoryList = new ArrayList<>();
			for (Category category : pages.getContent()) {
				Map<String, Object> data= Map.of(
							"rowId", category.getId(),
							"categoryName", category.getName(),
							"products", category.getProducts()
						);
				categoryList.add(data);
			}
			Map<String, Object> reponse = Map.of(
					"totalPages", pages.getTotalPages(),
					"totalRecords", pages.getTotalElements(),
					"categoryList", categoryList
				);
		
			return ResponseEntity.ok().body(reponse);
		}
		
		return ResponseEntity.badRequest().body("Invalid Request");
		
    }
	
	@PostMapping("/createCategory")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDTO category) {
       Category save = categoryService.createCategory(category);
       if(save != null) {
    	   return ResponseEntity.ok().body("Category saved successfully");
       }
       return ResponseEntity.badRequest().body("Unable to save Category");
    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if(category != null) {
        	return ResponseEntity.ok().body(category);
        }
        
        return ResponseEntity.ok().body("No category found");       
    }

	@PutMapping("/updateCategory")
	public ResponseEntity<Object> updateCategory(@RequestBody CategoryDTO categoryDTO) {
		Category category = categoryService.getCategoryById(Long.valueOf(categoryDTO.id()));
		if(category != null) {
			// update Category
			category.setId(category.getId());
			category.setName(
						!categoryDTO.name().isBlank() ? categoryDTO.name() : category.getName()
					);
			
			Category save = categoryRepo.save(category);
			return ResponseEntity.ok().body(save != null ? "Category Updated Successfully" : "Category update failed");
		}else {
			return ResponseEntity.ok().body("No such Category found to update");
		}
		
	
	}

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        if (categoryService.getCategoryById(id) != null) {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok().body("Category deleted");
        }
        return ResponseEntity.ok().body("No Category Found to Delete");
    }
}
