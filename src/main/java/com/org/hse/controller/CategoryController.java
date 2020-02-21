package com.org.hse.controller;

import java.util.List;
import java.util.Set;

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
import com.org.hse.dto.CategoryDto;
import com.org.hse.entity.Category;
import com.org.hse.entity.Product;
import com.org.hse.exception.EntityNotFoundException;
import com.org.hse.service.CategoryService;
import com.org.hse.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * This controller handles the CRUD operations for categories.
 * 
 * @author Abhishek
 *
 */
@RestController
@RequestMapping(path = "/categories")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<Category> addCategory(@RequestBody @Valid CategoryDto request) {

		log.info("Creating category");
		return ResponseEntity.ok(categoryService.addCategory(request.getName()));
	}

	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories() {

		log.info("Getting all the categories");
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {

		log.info("Getting category by id");
		final Category category = categoryService.getCategoryById(id)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.CATEGORY_NOT_FOUND));

		return ResponseEntity.ok(category);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto request) {

		log.info("Updating category by id");
		final Category category = categoryService.getCategoryById(id)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.CATEGORY_NOT_FOUND));

		return ResponseEntity.ok(categoryService.updateCategory(category, request.getName()));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {

		log.info("Deleting category by id");
		final Category category = categoryService.getCategoryById(id)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.CATEGORY_NOT_FOUND));

		categoryService.deleteCategory(category);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/{categoryId}/subcategories")
	public ResponseEntity<Set<Category>> getAllSubcategories(@PathVariable Long categoryId) {

		log.info("Getting sub categories for a category");
		final Category parent = categoryService.getCategoryById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.PARENT_CATEGORY_NOT_FOUND));

		return ResponseEntity.ok(parent.getSubCategories());
	}

	@PostMapping(path = "/{categoryId}/subcategories/{subCategoryId}")
	public ResponseEntity<?> addSubcategory(@PathVariable Long categoryId, @PathVariable Long subCategoryId) {

		log.info("Adding subcategory for a category");
		final Category parentCategory = categoryService.getCategoryById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.PARENT_CATEGORY_NOT_FOUND));

		final Category subCategory = categoryService.getCategoryById(subCategoryId)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.SUBCATEGORY_NOT_FOUND));

		if (categoryService.isExistingSubCategory(subCategory, parentCategory)) {
			log.error("Category: {} contains subcategory: {}", parentCategory.getId(), subCategory.getId());
			throw new IllegalArgumentException();
		}

		categoryService.addSubCategory(subCategory, parentCategory);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = "/{categoryId}/subcategories/{subCategoryId}")
	public ResponseEntity<?> deleteSubcategoryRelation(@PathVariable Long categoryId,
			@PathVariable Long subCategoryId) {

		log.info("De-linking category and subcategory");
		final Category parentCategory = categoryService.getCategoryById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.PARENT_CATEGORY_NOT_FOUND));

		final Category subCategory = categoryService.getCategoryById(subCategoryId)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.SUBCATEGORY_NOT_FOUND));

		if (!categoryService.isExistingSubCategory(subCategory, parentCategory)) {
			log.error("Category: {} does not contain subcategory: {}", parentCategory.getId(), subCategory.getId());
			throw new IllegalArgumentException();
		}

		categoryService.deleteSubcategoryRelation(subCategory);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(path = "/{categoryid}/products/{productid}")
	public ResponseEntity<Product> addCategoryToProduct(@PathVariable Long categoryid, @PathVariable Long productid) {

		log.info("Mapping category to product");
		final Category category = categoryService.getCategoryById(categoryid)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.PARENT_CATEGORY_NOT_FOUND));

		final Product product = productService.getProductById(productid)
				.orElseThrow(() -> new EntityNotFoundException(AppConstants.PRODUCT_NOT_FOUND));

		if (productService.hasCategory(product, category)) {
			
			log.error("product {} already contains category {}", product.getId() ,category.getId());
			throw new IllegalArgumentException();
		}

		return ResponseEntity.ok(productService.addCategoryToProduct(product, category));
	}

}
