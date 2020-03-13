package com.starter.grocerystore.common;

import java.io.Serializable;
import java.util.Collection;

public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {
	<S extends T> S save(S entity);
	T findOne(ID primaryKey);
	Collection<T> findAll();
	Long count();
	void delete(T entity);
	boolean exists(ID primaryKey);
}
