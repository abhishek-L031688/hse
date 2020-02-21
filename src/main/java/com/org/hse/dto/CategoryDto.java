package com.org.hse.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * DTO class for categories
 * @author Abhishek
 *
 */
@Data
public class CategoryDto {

	@NotNull(message = "name is required")
	@Size(message = "name must be equal to or lower than 100", min = 1, max = 100)
	private String name;

}
