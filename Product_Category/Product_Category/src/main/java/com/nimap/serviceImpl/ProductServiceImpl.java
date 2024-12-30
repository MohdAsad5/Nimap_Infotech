package com.nimap.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nimap.dto.ProductCategoryDTO;
import com.nimap.dto.ProductDTO;
import com.nimap.entity.Category;
import com.nimap.entity.Product;
import com.nimap.repository.ProductRepository;
import com.nimap.service.CategoryService;
import com.nimap.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public Page<Product> getAllProducts(int page) {
		return productRepo.findAll(PageRequest.of(page, 10));
	}

	@Override
	public Product createProduct(ProductDTO productDTO) {
		Product product = new Product();
		//create product
		product.setName(productDTO.name());
		product.setPrice(Double.valueOf(productDTO.price()));
		product.setColor(productDTO.color());
		product.setBrand(productDTO.brand());
		product.setDetails(productDTO.details());
		Category category = categoryService.getCategoryById(Long.valueOf(productDTO.categoryId()));
		if(category == null) {
			return null;
		}
		product.setCategory(category);
		
		return productRepo.save(product);
	}

	@Override
	public ProductCategoryDTO getProductById(Long id) {
		ProductCategoryDTO product = productRepo.getProductByCategoryId(id);
		if(product != null) {
			return product;
		}
		return null;
	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}

}
