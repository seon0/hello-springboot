package com.example.demo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(title = "Demo API", version = "v1"),
		security = {
				@SecurityRequirement(name = "bearerAuth")
		}
)
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
						.info( new Info()
										.title("Demo API")
										.description("Spring Boot + JWT + Swagger API 문서")
										.version("1.0.0") 
										)
						.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("Authorization"))
//						.components(new Components()
//								.addSecuritySchemes("AUthorization", 
//										new io.swagger.v3.oas.models.security.SecurityScheme()
//												.name("Authorization")
//												.type(SecurityScheme.Type.HTTP)
//												.scheme("bearer")
//												.bearerFormat("JWT")
//								)
//						)
				;
	}

}
