package br.com.vivo.catalog.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vivo.catalog.product.model.Product;
import br.com.vivo.catalog.product.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public List<Product> getProducts() {
		return productRepository.findAll();
	}
	
	public Product getProduct(String productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			return product.get();
		}
		return null;
	}
	
	public List<Product> searchProductsByFilters(BigDecimal minimumPrice, BigDecimal maximumPrice, String query) {
		return productRepository.getProductsByNameDescriptionAndPriceRange(minimumPrice, maximumPrice, query);
	}
	
	@Transactional
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	@Transactional
	public Product updateProduct(String productId, Product updatedProduct) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			product.get().setName(updatedProduct.getName());
			product.get().setDescription(updatedProduct.getDescription());
			product.get().setPrice(updatedProduct.getPrice());
			productRepository.save(product.get());
			return product.get();
		} else {
			return null;
		}
	}
	
	@Transactional
	public Boolean deleteProduct(String productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			productRepository.findById(productId);
			productRepository.deleteById(productId);
			return true;
		} else {
			return false;
		}
	}
	
}
