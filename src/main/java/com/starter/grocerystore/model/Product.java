package com.starter.grocerystore.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString @EqualsAndHashCode @Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 7422885688712155838L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String productName;
	private Long retailPrice;
	private Integer quantityInStock;
	
	public Product(int id, String productName, long retailPrice, int quantityInStock) {
		this.id = id;
		this.productName = productName;
		this.retailPrice = retailPrice;
		this.quantityInStock = quantityInStock;
	}
}
