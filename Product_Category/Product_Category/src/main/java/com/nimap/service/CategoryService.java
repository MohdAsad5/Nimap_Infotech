package com.nimap.service;

import org.springframework.data.domain.Page;

import com.nimap.dto.CategoryDTO;
import com.nimap.entity.Category;

public interface CategoryService {
	
	public Page<Category> getAllCategories(int page);
	public Category createCategory(CategoryDTO category);
	public Category getCategoryById(Long id);
	public void deleteCategoryById(Long id);
}
