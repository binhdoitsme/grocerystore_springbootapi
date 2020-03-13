package com.starter.grocerystore.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor @Getter @ToString
public class ProductDto {
	private @NonNull Integer id;
	private @NonNull String productName;
	private @NonNull Long retailPrice;
	private @NonNull Integer quantity;
}
