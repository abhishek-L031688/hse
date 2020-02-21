package com.org.hse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.hse.entity.Product;

/**
 * Repository for products
 * @author Abhishek
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
