package br.com.vivo.catalog.product.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.vivo.catalog.product.dto.ProductDTO;
import br.com.vivo.catalog.product.dto.ProductFormDTO;
import br.com.vivo.catalog.product.dto.UpdateProductFormDTO;
import br.com.vivo.catalog.product.mapper.ProductMapper;
import br.com.vivo.catalog.product.model.Product;
import br.com.vivo.catalog.product.service.ProductCatalogManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/products")
@Validated
@Api(value = "Product Catalog Management Spring Boot REST API")
@CrossOrigin(origins = "*")
public class ProductController {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductCatalogManagementService productService;
	
	@GetMapping
	@ApiOperation(value = "Get all products registered")
	public List<ProductDTO> getProducts() {
		return productMapper.productsToProductsDTO(productService.getProducts());
	}
	
	@GetMapping("/{productId}")
	@ApiOperation(value = "Get existing product by identifier")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
		if (productService.getProduct(productId) != null) {
			return ResponseEntity.ok(productMapper.productToProductDTO(productService.getProduct(productId)));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/search")
	@ApiOperation(value = "Get specific products filtered by name and description and between minimum and maximum price")
	public List<ProductDTO> getProductsByFilters(@RequestParam(required = false, name = "min_price") @DecimalMin(value = "0.00", inclusive = false, message = "Minimum price may not be empty or negative or zero") BigDecimal minimumPrice, 
												 @RequestParam(required = false, name = "max_price") @DecimalMin(value = "0.00", inclusive = false, message = "Maximum price may not be empty or negative or zero") BigDecimal maximumPrice, 
												 @RequestParam(required = false, name = "q") String query) {
		return productMapper.productsToProductsDTO(productService.searchProductsByFilters(minimumPrice, maximumPrice, query));
	}
	
	@PostMapping
	@ApiOperation(value = "Create a new product")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductFormDTO productForm, UriComponentsBuilder uriBuilder) {
		Product product = productMapper.productFormToProduct(productForm);
		Product productCreated = productService.createProduct(product);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productCreated.getId()).toUri();
		return ResponseEntity.created(uri).body(productMapper.productToProductDTO(productCreated));
	}
	
	@PutMapping("/{productId}")
	@ApiOperation(value = "Update existing product by identifier")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @RequestBody @Valid UpdateProductFormDTO updateProductForm) {
		Product updatedProduct = productMapper.updateProductFormToProduct(updateProductForm);
		Product product = productService.updateProduct(productId, updatedProduct);
		if (product != null) {
			return ResponseEntity.ok(productMapper.productToProductDTO(product));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{productId}")
	@ApiOperation(value = "Delete existing product by identifier")
	public ResponseEntity<?> removeProduct(@PathVariable String productId) {
		if (productService.deleteProduct(productId) == true) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
