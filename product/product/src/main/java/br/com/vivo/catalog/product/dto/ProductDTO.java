package br.com.vivo.catalog.product.dto;

import java.math.BigDecimal;

import br.com.vivo.catalog.product.model.Product;

public class ProductDTO {

	private String id;
	private String name;
	private String description;
	private BigDecimal price;
	
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
}
