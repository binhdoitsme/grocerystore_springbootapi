package com.starter.grocerystore.dto.mapper;

import org.springframework.stereotype.Component;

import com.starter.grocerystore.dto.ProductDto;
import com.starter.grocerystore.model.Product;

@Component
public class ProductMapper {
	public static ProductDto toProductDto(Product p, Integer qty) {
		return new ProductDto(p.getId(), p.getProductName(), p.getRetailPrice(), qty);
	}
}
