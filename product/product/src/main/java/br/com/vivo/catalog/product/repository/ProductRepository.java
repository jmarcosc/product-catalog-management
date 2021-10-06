package br.com.vivo.catalog.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vivo.catalog.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE (:minimumPrice IS NULL OR p.price >= :minimumPrice) AND (:maximumPrice IS NULL OR p.price <= :maximumPrice) AND ( :query IS NULL OR UPPER(p.name) LIKE UPPER(CONCAT('%',:query,'%')) OR UPPER(p.description) LIKE UPPER(CONCAT('%',:query,'%')))")
	public List<Product> getProductsByNameDescriptionAndPriceRange(@Param("minimumPrice") BigDecimal minimumPrice, 
																   @Param("maximumPrice") BigDecimal maximumPrice, 
																   @Param("query") String query);
	
	
	
}
