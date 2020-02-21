package com.org.hse.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.org.hse.dto.ProductDto;
import com.org.hse.entity.Category;
import com.org.hse.entity.Product;

public interface ProductService {

	/**Adds a product
	 * @param request
	 * @return
	 */
	Product addProduct(@Valid ProductDto request);

	/**Returns all the products
	 * @return
	 */
	List<Product> getAllProducts();

	/**Returns product by id
	 * @param id
	 * @return
	 */
	Optional<Product> getProductById(Long id);

	/**Update Product
	 * @param product
	 * @param request
	 * @return
	 */
	Product updateProduct(Product product, @Valid ProductDto request);

	/**Deletes the product
	 * @param product
	 */
	void deleteProduct(Product product);

	/**Checks if a product is mapped
	 * to a category
	 * @param product
	 * @param category
	 * @return
	 */
	boolean hasCategory(Product product, Category category);

	/**Adds Category to product
	 * @param product
	 * @param category
	 * @return
	 */
	Product addCategoryToProduct(Product product, Category category);

	/**Returns product with categories tree
	 * @param id
	 * @return
	 */
	Optional<ProductDto> getProductWithCategories(Long id);

}
