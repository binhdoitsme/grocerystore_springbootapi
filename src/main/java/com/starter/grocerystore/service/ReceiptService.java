package com.starter.grocerystore.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.starter.grocerystore.dto.ProductDto;
import com.starter.grocerystore.model.Product;
import com.starter.grocerystore.model.Receipt;

import lombok.Cleanup;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Service
class ReceiptService {
	private Receipt receiptReference;
	private final String receiptFolderPath = "src/main/resources/receipts/";
	
	public void createReceipt() {
		receiptReference = new Receipt();
	}
	
	public boolean isActive() {
		if (receiptReference == null) {
			return false;
		} else {
			return receiptReference.initialized();
		}
	}
	
	public ProductDto addReceiptLine(Product p, int quantity) {
		return receiptReference.addReceiptLine(p, quantity);
	}
	
	public Map<Product, Integer> getReceiptLines() {
		if (receiptReference == null) return null;
		return receiptReference.getProducts();
	}
	
	public void removeReceiptLine(Product p) {
		this.receiptReference.getProducts().remove(p);
	}
	
	private String writeReceipt(String path) {
		StringBuilder receiptBuilder = new StringBuilder();
		try {
			File f = new File(path);
			f.createNewFile();
			@Cleanup BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			receiptBuilder.append(String.format("%25s%d%n", "RECEIPT #", receiptReference.getId()));
			receiptBuilder.append(System.lineSeparator());
			receiptBuilder.append(System.lineSeparator());
			String lineFormat = "%5s %50s %5s %15s%n";
			AtomicLong total = new AtomicLong(0);
			AtomicInteger listIndex = new AtomicInteger(0);
			receiptBuilder.append(String.format(lineFormat, "#", "PRODUCT NAME", "QTY", "LIST PRICE"));
			receiptReference.getProducts().forEach((k, v) -> {
				String productNameShort = k.getProductName().length() > 50 ? k.getProductName().substring(0, 50) : k.getProductName();
				receiptBuilder.append(String.format(lineFormat, 
										listIndex.incrementAndGet(), 
										productNameShort, 
										v, 
										v * k.getRetailPrice()));
				total.addAndGet(v * k.getRetailPrice());
			});
			receiptBuilder.append(String.format(lineFormat, "TOTAL:", "", "", total));
			receiptBuilder.append(System.lineSeparator());
			receiptBuilder.append(System.lineSeparator());
			receiptBuilder.append("Receipt created at " + LocalDateTime.now().toString() + ".");
			
			// write
			String s = receiptBuilder.toString();
			bw.append(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receiptBuilder.toString();
	}
	
	public String writeReceipt() {
		String path = receiptFolderPath + "receipt_" + Long.toString(receiptReference.getId()) + ".txt";
		String receiptString = writeReceipt(path);
		clear();
		return receiptString;
	}
	
	public void clear() {
		receiptReference = null;
	}
	
	@Override
	public String toString() {
		if (receiptReference == null) {
			return getClass().getSimpleName() + ":(Status: Pending)";
		}
		else {
			return getClass().getSimpleName() + ":(Status: Processing, " + receiptReference.toString() + ")";
		}
	}
}
