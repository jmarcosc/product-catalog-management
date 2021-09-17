package br.com.vivo.catalog.product.dto;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.vivo.catalog.product.model.Product;
import br.com.vivo.catalog.product.repository.ProductRepository;

public class UpdateProductFormDTO {

	@NotBlank(message = "Name may not be empty")
	private String name;
	
	@NotBlank(message = "Description may not be empty")
	private String description;
	
	@NotNull @DecimalMin(value = "0.00", inclusive = false, message = "Price may not be empty, negative or zero")
	private BigDecimal price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Product update(String productId, ProductRepository productRepository) {
		Optional<Product> product = productRepository.findById(productId);
		product.get().setName(this.name);
		product.get().setDescription(this.description);
		product.get().setPrice(this.price);
		return product.get();
	}
	
}
