package br.com.vivo.catalog.product.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.vivo.catalog.product.dto.ProductDTOResponse;
import br.com.vivo.catalog.product.dto.ProductDTORequest;
import br.com.vivo.catalog.product.model.Product;

@Component
public class ProductMapper {

	public List<ProductDTOResponse> productsToProductsDTO(List<Product> products) {
		return products.stream().map(ProductDTOResponse::new).collect(Collectors.toList());
	}
	
	public ProductDTOResponse productToProductDTO(Product product) {
		return new ProductDTOResponse(product);
	}
	
	public Product productDTOToProduct(ProductDTORequest productForm) {
		return new Product(productForm.getName(), productForm.getDescription(), productForm.getPrice());
	}
	
	public Product updateProductToProduct(ProductDTORequest updateProduct) {
		return new Product(updateProduct.getName(), updateProduct.getDescription(), updateProduct.getPrice());
	}
	
}
