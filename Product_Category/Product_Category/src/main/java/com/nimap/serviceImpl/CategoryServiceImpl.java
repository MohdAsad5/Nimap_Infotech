package com.nimap.serviceImpl;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nimap.dto.CategoryDTO;
import com.nimap.entity.Category;
import com.nimap.repository.CategoryRepository;
import com.nimap.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Page<Category> getAllCategories(int page) {
		return categoryRepo.findAll(PageRequest.of(page, 10));
		
	}

	@Override
	public Category createCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		
		// create category
		category.setName(categoryDTO.name());
		category.setProducts(categoryDTO.product());
		return categoryRepo.save(category);
	}

	@Override
	public Category getCategoryById(Long id) {
		Optional<Category> category = categoryRepo.findById(id);
		if(category.isPresent()) {
			return category.get();
		}
		
		return null;
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryRepo.deleteById(id);;
		
	}

}
