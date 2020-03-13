package com.starter.grocerystore.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.starter.grocerystore.dto.ProductDto;
import com.starter.grocerystore.dto.mapper.ProductMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor @Getter @ToString @EqualsAndHashCode
public class Receipt {
	private final long id;
	private Map<Product, Integer> products;
	
	public Receipt() {
		this.id = issueReceiptId();
	}
	
	private long issueReceiptId() {
		LocalDateTime dt = LocalDateTime.now();
		StringBuilder sb = new StringBuilder();
		sb.append(dt.getYear()).append(dt.getMonthValue()).append(dt.getDayOfMonth())
			.append(dt.getHour()).append(dt.getMinute()).append(dt.getSecond());
		return Long.parseLong(sb.toString());
	}
	
	public ProductDto addReceiptLine(Product p, int qty) {
		int quantity = 0;
		if (products == null) {
			products = new HashMap<>();
		}
		if (products.containsKey(p)) {
			quantity = products.get(p);
		}
		quantity += qty;
		products.put(p, quantity);
		return ProductMapper.toProductDto(p, quantity);
	}
	
	public boolean initialized() {
		return products != null;
	}
}
