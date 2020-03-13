package com.starter.grocerystore.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starter.grocerystore.dto.ProductDto;
import com.starter.grocerystore.dto.mapper.ProductMapper;
import com.starter.grocerystore.model.Product;

@Service
public class CheckoutService {
	ReceiptService receiptService;
	ProductService productService;
	
	@Autowired
	public CheckoutService(ReceiptService receiptService, ProductService productService) {
		this.receiptService = receiptService;
		this.productService = productService;
		receiptService.createReceipt();
	}
	
	public ProductDto addProductToCart(Product p, int qty) {
		if (!receiptService.isActive()) {
			receiptService.createReceipt();
		}
		return receiptService.addReceiptLine(p, qty);
	}
	
	public void removeProductFromCart(Product p) {
		if (!receiptService.isActive()) {
			receiptService.createReceipt();
		}
		receiptService.removeReceiptLine(p);
	}
	
	public void emptyCart() {
		receiptService.clear();
	}
	
	public void takeProductFromStock(Product p, int qty) {
		int currentQty = p.getQuantityInStock();
		p.setQuantityInStock(currentQty - qty);
		productService.updateProduct(p);		
	}
	
	public void takeProductsFromStock() {
		Map<Product, Integer> products = receiptService.getReceiptLines();
		products.forEach((p, qty) -> {
			takeProductFromStock(p, qty);
		});
	}
	
	public void printReceipt() {
		receiptService.writeReceipt();
	}
	
	public void clear() {
		receiptService = new ReceiptService();
	}
	
	public Collection<ProductDto> cart() {
		List<ProductDto> productLines = new ArrayList<ProductDto>();
		if (receiptService.isActive()) {
			receiptService.getReceiptLines().forEach((p, q) -> {
				productLines.add(ProductMapper.toProductDto(p, q));
			});
		}
		return productLines;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ":(" + receiptService.toString() + ")";
	}
}
