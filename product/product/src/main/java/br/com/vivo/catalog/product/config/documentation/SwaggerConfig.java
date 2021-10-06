package br.com.vivo.catalog.product.config.documentation;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.vivo.catalog.product"))
                .paths(regex("/products.*"))
                .build()
                .apiInfo(metaInfo());
    }
	
	private ApiInfo metaInfo() {

        return new ApiInfo(
                "Product Catalog Management Spring Boot REST API",
                "Spring Boot REST API for product catalog management including getting, registering, updating and deleting products operations",
                "1.0",
                "Terms of Service",
                new Contact("João Marcos da Costa", "https://github.com/jmarcosc",
                        "costa.joaomarcos@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/license.html", new ArrayList<>()
        );
        
    }
	
}
