package com.org.hse.service;

import java.util.List;
import java.util.Optional;

import com.org.hse.entity.Category;

/**
 * @author Abhishek
 *
 */
public interface CategoryService {

	/**Create category in the system
	 * @param name
	 * @return
	 */
	Category addCategory(String name);

	/**Returns all the categories
	 * @return
	 */
	List<Category> getAllCategories();

	/**Returns category by id
	 * @param id
	 * @return
	 */
	Optional<Category> getCategoryById(Long id);

	/**Updates category by id
	 * @param category
	 * @param name
	 * @return
	 */
	Category updateCategory(Category category, String name);

	/**Deletes a category by id
	 * @param category
	 */
	void deleteCategory(Category category);

	/**Validates if the category is already
	 * a subcategory
	 * @param subCategory
	 * @param parentCategory
	 * @return
	 */
	boolean isExistingSubCategory(Category subCategory, Category parentCategory);

	/**Add a subcategory
	 * @param subCategory
	 * @param parentCategory
	 */
	void addSubCategory(Category subCategory, Category parentCategory);

	/**Delete category and subcategory relation
	 * @param subCategory
	 */
	void deleteSubcategoryRelation(Category subCategory);

}
