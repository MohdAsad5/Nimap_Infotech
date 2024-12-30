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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimap.dto.ProductCategoryDTO;
import com.nimap.dto.ProductDTO;
import com.nimap.entity.Category;
import com.nimap.entity.Product;
import com.nimap.repository.ProductRepository;
import com.nimap.service.CategoryService;
import com.nimap.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired 
	ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/getAllProduct")
    public ResponseEntity<Object> getAllCategories(@RequestParam(defaultValue = "0") int page) {
		Page<Product> pages = productService.getAllProducts(page);
		List<Map<String, Object>> productList = null;
		if(pages != null) {
			productList = new ArrayList<>();
			for (Product product : pages.getContent()) {
				Map<String, Object> data= Map.of(
							"rowId", product.getId(),
							"productName", product.getName(),
							"brand", product.getBrand(),
							"color", product.getColor(),
							"productDetails",product.getDetails(),
							"productCategory", product.getCategory() != null ? 
												product.getCategory().getName() : ""
						);
				productList.add(data);
			}
			Map<String, Object> response = Map.of(
					"totalPages", pages.getTotalPages(),
					"totalRecords", pages.getTotalElements(),
					"productList", productList
				);
		
			return ResponseEntity.ok().body(response);
		}
		
		return ResponseEntity.badRequest().body("Invalid Request");
		
    }
	
	@PostMapping("/createProduct")
    public ResponseEntity<Object> createCategory(@RequestBody ProductDTO productDTO) {
       Product save = productService.createProduct(productDTO);
       if(save != null) {
    	   return ResponseEntity.ok().body("Product saved successfully");
       }
       return ResponseEntity.badRequest().body("No Product Category Found");
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        ProductCategoryDTO product = productService.getProductById(id);
        ObjectMapper objMapper = null;
        if(product != null) {
        	objMapper = new ObjectMapper();
        	Map<String, Object> response = objMapper.convertValue(product, Map.class);
        	return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.ok().body("No Product found");       
    }

	@PutMapping("/updateProduct")
	public ResponseEntity<Object> updateProduct(@RequestBody ProductDTO productDTO) {
		ProductCategoryDTO productById = productService.getProductById(Long.valueOf(productDTO.id()));
		Product product = null;
		if(productById != null) {
			product = new Product();
			product.setId(Long.valueOf(productDTO.id()));
			product.setName(
						productDTO.name() != null ? productDTO.name() : productById.getProductName()
					);
			product.setPrice(
						productDTO.price() != null ? Double.valueOf(productDTO.price()) : productById.getProductPrice()
					);
			product.setColor(
							productDTO.color() != null ? productDTO.color() : productById.getProductColor()
						);
			product.setBrand(
							productDTO.brand() != null ? productDTO.brand() : productById.getProductBrand()
						);
			product.setDetails(
						productDTO.details() != null ? productDTO.details() : productById.getProductDetails()
					);
			Category category = categoryService.getCategoryById(Long.valueOf(productDTO.categoryId()));
			product.setCategory(category);
			Product save = productRepo.save(product);
			return ResponseEntity.ok().body(save != null ? "Product Updated Successfully" : "Product update failed");
		}else
			return ResponseEntity.ok().body("No Product found to update");
	
	}

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        if (productRepo.findById(id) != null) {
        	productService.deleteProduct(id);
            return ResponseEntity.ok().body("Product deleted");
        }
        return ResponseEntity.ok().body("No Product Found to Delete");
    }
}
