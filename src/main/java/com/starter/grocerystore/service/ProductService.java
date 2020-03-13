package com.starter.grocerystore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starter.grocerystore.model.Product;
import com.starter.grocerystore.repository.product.ProductRepository;

@Service
public class ProductService {	
	@Autowired
	ProductRepository repository;
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(int id) {
		Optional<Product> result = repository.findById(id);
		return result.isPresent() ? result.get() : null;
	}
	
	public List<Product> findByNameContaining(String partial) {
		return repository.findByProductNameContaining(partial);
	}
	
	public void updateProduct(Product p) {
		repository.updateProduct(p.getId(), p.getProductName(), p.getRetailPrice(), p.getQuantityInStock());
	}
}
