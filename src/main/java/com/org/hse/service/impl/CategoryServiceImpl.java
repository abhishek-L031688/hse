package com.org.hse.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.hse.entity.Category;
import com.org.hse.repository.CategoryRepository;
import com.org.hse.service.CategoryService;

/**
 * Service implementation for CategoryService
 * 
 * @author Abhishek
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category addCategory(String name) {
		Category category = new Category();
		category.setName(name);

		return categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category updateCategory(Category category, String name) {
		category.setName(name);
        return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}
	
	@Override
	public boolean isExistingSubCategory(Category subCategory, Category parentCategory) {
		return parentCategory.equals(subCategory.getParent());
	}

	@Override
	public void addSubCategory(Category subCategory, Category parentCategory) {
		subCategory.setParent(parentCategory);
        categoryRepository.save(subCategory);
	}

	@Override
	public void deleteSubcategoryRelation(Category subCategory) {
		subCategory.setParent(null);
        categoryRepository.save(subCategory);
	}

}
