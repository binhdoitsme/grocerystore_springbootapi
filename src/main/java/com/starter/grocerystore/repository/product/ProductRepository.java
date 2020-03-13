package com.starter.grocerystore.repository.product;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.starter.grocerystore.common.CrudRepository;
//import com.starter.grocerystore.model.Product;
//
///**
// * In-memory stub repository
// * @author Binh_Do
// *
// */
//public class ProductRepository implements CrudRepository<Product, Integer> {
//	private Map<Integer, Product> products;
//	
//	public ProductRepository() {
//		products = new HashMap<Integer, Product>() {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = -2261869743785958052L;
//
//			{
//				put(1, new Product(1, "Toonies Snack Cinematic Edition", 7000, 100));
//				put(2, new Product(2, "Wake-up 24-7 Limited Edition", 9000, 24));
//				put(3, new Product(3, "Oishi CrabMe", 5000, 200));
//				put(4, new Product(4, "Sting Ginseng Flavor Energy Drink", 8000, 120));
//				put(5, new Product(5, "Monster Original Flavor Energy Drink", 30000, 60));
//				put(6, new Product(6, "Hao Hao Shrimp Noodles", 3000, 20));
//			}
//		};
//	}
//
//	@Override
//	public <S extends Product> S save(S entity) {
//		products.put(entity.getId(), entity);
//		return entity;
//	}
//
//	@Override
//	public Product findOne(Integer primaryKey) {
//		if (products.containsKey(primaryKey)) {
//			return products.get(primaryKey);
//		} else {
//			return null;
//		}
//	}
//
//	@Override
//	public Collection<Product> findAll() {
//		return new ArrayList<Product>(products.values());
//	}
//
//	@Override
//	public Long count() {
//		return (long)products.size();
//	}
//
//	@Override
//	public void delete(Product entity) {
//		products.remove(entity.getId());
//	}
//
//	@Override
//	public boolean exists(Integer primaryKey) {
//		return products.containsKey(primaryKey);
//	}
//}

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.starter.grocerystore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByProductName(String productName);
	List<Product> findByProductNameContaining(String productName);
	@Query("UPDATE Product p SET p.productName = :pname, p.retailPrice = :prprice, p.quantityInStock = :pqty WHERE p.id = :pid")
	@Transactional @Modifying
	void updateProduct(@Param("pid")int id, @Param("pname")String newName, @Param("prprice")long newRetailPrice, @Param("pqty")int newQuantity);
}