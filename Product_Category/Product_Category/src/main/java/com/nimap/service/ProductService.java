package com.nimap.service;

import org.springframework.data.domain.Page;

import com.nimap.dto.ProductCategoryDTO;
import com.nimap.dto.ProductDTO;
import com.nimap.entity.Product;

public interface ProductService {
	
	public Page<Product> getAllProducts(int page);
	public Product createProduct(ProductDTO product);
	public ProductCategoryDTO getProductById(Long id);
	public void deleteProduct(Long id);
}
