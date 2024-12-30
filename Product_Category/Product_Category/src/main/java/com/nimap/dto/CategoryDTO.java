package com.nimap.dto;

import java.util.List;

import com.nimap.entity.Product;

public record CategoryDTO(
			String id,
			String name,
			List<Product> product
		) {
	
}
