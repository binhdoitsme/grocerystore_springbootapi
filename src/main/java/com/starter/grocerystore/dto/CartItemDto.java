package com.starter.grocerystore.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter(value = AccessLevel.MODULE) @NoArgsConstructor @ToString
public class CartItemDto {
	private @NonNull Integer id;
	private @NonNull Integer qty;
}
