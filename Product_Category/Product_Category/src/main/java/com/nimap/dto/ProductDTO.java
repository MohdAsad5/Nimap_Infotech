package com.nimap.dto;

public record ProductDTO(
		String id,
	    String name,
	    String price,
	    String color,
	    String brand,
	    String details,
	    Long categoryId

		) {

}
