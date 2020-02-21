package com.org.hse.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for products
 * 
 * @author Abhishek
 *
 */
@Data
@NoArgsConstructor
public class ProductDto {

	@NotNull(message = "name is required")
	@Size(message = "name must be equal to or lower than 300", min = 1, max = 300)
	private String name;
	@NotNull
	private String currency;
	@NotNull(message = "Price is required")
	@Min(0)
	private Double price;
	private List<List<String>>  categories;

}
