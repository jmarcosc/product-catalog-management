package br.com.vivo.catalog.product.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.vivo.catalog.product.dto.ProductDTO;
import br.com.vivo.catalog.product.dto.ProductFormDTO;
import br.com.vivo.catalog.product.dto.UpdateProductFormDTO;
import br.com.vivo.catalog.product.model.Product;

@Component
public class ProductMapper {

	public List<ProductDTO> productsToProductsDTO(List<Product> products) {
		return products.stream().map(ProductDTO::new).collect(Collectors.toList());
	}
	
	public ProductDTO productToProductDTO(Product product) {
		return new ProductDTO(product);
	}
	
	public Product productFormToProduct(ProductFormDTO productForm) {
		return new Product(productForm.getName(), productForm.getDescription(), productForm.getPrice());
	}
	
	public Product updateProductFormToProduct(UpdateProductFormDTO updateProductForm) {
		return new Product(updateProductForm.getName(), updateProductForm.getDescription(), updateProductForm.getPrice());
	}
	
}
