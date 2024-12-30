package com.nimap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimap.dto.ProductCategoryDTO;
import com.nimap.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT p.id as productId, p.name as productName, p.price as productPrice, " +
            "p.color as productColor, p.brand as productBrand, p.details as productDetails, " +
            "c.id as categoryId, c.name as categoryName " +
            "FROM Product p " +
            "LEFT JOIN Category c ON p.category_id = c.id " +
            "WHERE c.id = :categoryId", nativeQuery = true)
	ProductCategoryDTO getProductByCategoryId(@Param("categoryId") Long categoryId);
	
}
