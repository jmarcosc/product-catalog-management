package br.com.vivo.product;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.vivo.catalog.product.ProductApplication;
import br.com.vivo.catalog.product.model.Product;
import br.com.vivo.catalog.product.repository.ProductRepository;

@SpringBootTest(classes = ProductApplication.class)
class ProductApplicationTests {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void createProduct() {
		Product product = new Product();
		product.setName("LG K8");
		product.setDescription("Tela plana 4GB");
		product.setPrice(new BigDecimal("1250.00"));
		productRepository.save(product);
	}

}
