package com.org.hse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.hse.dto.ProductDto;
import com.org.hse.entity.Category;
import com.org.hse.entity.Product;
import com.org.hse.repository.CategoryRepository;
import com.org.hse.repository.ProductRepository;
import com.org.hse.service.ProductService;

/**
 * Service implementation for ProductService
 * 
 * @author Abhishek
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Product addProduct(@Valid ProductDto request) {

		Product product = new Product();
		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setCurrency(request.getCurrency());

		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product updateProduct(Product product, @Valid ProductDto request) {

		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setCurrency(request.getCurrency());
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}

	@Override
	public boolean hasCategory(Product product, Category category) {
		return product.getCategories().contains(category);
	}

	@Override
	public Product addCategoryToProduct(Product product, Category category) {
		product.getCategories().add(category);
		return productRepository.save(product);
	}
	
	@Override
	public Optional<ProductDto> getProductWithCategories(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		
		if(!optionalProduct.isPresent()) {
			return Optional.empty();
		}
		Product product = optionalProduct.get();
		
		ProductDto productDto = new ProductDto();
		productDto.setName(product.getName());
		productDto.setCurrency(product.getCurrency());
		productDto.setPrice(product.getPrice());
		
		List<List<String>> categories = new ArrayList<>();
		
		for(Category cat : product.getCategories()) {
			
			categories.add(categoryRepository.findCategories(cat.getId()));
		}
		productDto.setCategories(categories);
		
		return Optional.of(productDto);
	}

}
