package com.starter.grocerystore.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starter.grocerystore.dto.CartItemDto;
import com.starter.grocerystore.dto.ProductDto;
import com.starter.grocerystore.model.Product;
import com.starter.grocerystore.service.CheckoutService;
import com.starter.grocerystore.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GroceryStoreController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CheckoutService checkoutService;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/")
	public void doNothing() {
		
	}
	
	@GetMapping(path="/products", produces="application/json")
	public List<Product> getProducts() {
		return productService.findAll();
	}
	
	@GetMapping(path="/product", produces="application/json")
	public Product getProductData(@RequestParam Integer id) {
		return productService.findById(id);
	}
	
	@PostMapping(path="/addcart", produces="application/json")
	public ProductDto addToCart(@RequestBody CartItemDto itm) {
		int id = itm.getId();
		int qty = itm.getQty();
		Product p = productService.findById(id);
		ProductDto pDto = checkoutService.addProductToCart(p, qty);
		logger.info(checkoutService.toString());
		return pDto;
	}
	
	@PostMapping("/removecart")
	public void removeFromCart(@RequestBody CartItemDto itm) {
		int id = itm.getId();
		Product p = productService.findById(id);
		checkoutService.removeProductFromCart(p);
		logger.info(checkoutService.toString());
	}
	
	@PostMapping("/emptycart")
	public void emptyCart() {
		checkoutService.emptyCart();
	}
	
	@PostMapping(path="/getcart", produces="application/json")
	public Collection<ProductDto> getCart() {
		return checkoutService.cart();
	}

	@PostMapping("/checkout")
	public void performCheckout() {
		checkoutService.takeProductsFromStock();
		checkoutService.printReceipt();
		checkoutService.clear();
	}
}
