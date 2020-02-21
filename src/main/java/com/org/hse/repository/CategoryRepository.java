package com.org.hse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.hse.entity.Category;

/**
 * Repository for Category
 * @author Abhishek
 *
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	String query = "WITH RECURSIVE ancestors(id, parentid, name) AS (\r\n" + 
			"              SELECT cat.id, cat.parentid, cat.name\r\n" + 
			"              FROM categories cat \r\n" + 
			"              WHERE cat.id = :categoryId \r\n" + 
			"              UNION ALL \r\n" + 
			"              SELECT parent.id, parent.parentid, parent.name \r\n" + 
			"              FROM categories parent \r\n" + 
			"              JOIN ancestors child \r\n" + 
			"              ON parent.id = child.parentid)SELECT name from ancestors";
	
	@Query(value = query, nativeQuery = true)
	List<String> findCategories(Long categoryId);
	

}
