package com.org.hse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.hse.constants.AppConstants;
import com.org.hse.dto.ProductDto;
import com.org.hse.entity.Product;
import com.org.hse.exception.EntityNotFoundException;
import com.org.hse.service.ProductService;

import lombok.extern.slf4j.Slf4j;


/**
 * This controller handles the CRUD operations
 * for the products
 * @author Abhishek
 *
 */
@RestController
@RequestMapping(path = "/products")
@Slf4j
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	@PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductDto request) {
		
		log.info("Adding product");
        return ResponseEntity.ok(productService.addProduct(request));
    }
	
	@GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
		
		log.info("Getting all the products");
        return ResponseEntity.ok(productService.getAllProducts());
    }
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        
		log.info("Getting Product By Id");
		final Product product = productService.getProductById(id)
            .orElseThrow(() -> new EntityNotFoundException(AppConstants.PRODUCT_NOT_FOUND));

		return ResponseEntity.ok(product);
    }
	
	@PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto request) {
        
		log.info("Updating Product id:" + id);
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new EntityNotFoundException(AppConstants.PRODUCT_NOT_FOUND));

        return ResponseEntity.ok(productService.updateProduct(product, request));
    }
	
	@DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		
		log.info("Deleting the product id: "+id);
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new EntityNotFoundException(AppConstants.PRODUCT_NOT_FOUND));

        productService.deleteProduct(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@GetMapping(path = "/{id}/categories")
    public ResponseEntity<ProductDto> getProductWithCategories(@PathVariable Long id) {
        
		log.info("Getting Product By Id");
		final ProductDto product = productService.getProductWithCategories(id)
            .orElseThrow(() -> new EntityNotFoundException(AppConstants.PRODUCT_NOT_FOUND));

		return ResponseEntity.ok(product);
    }
	
}
