package com.sukhanov.geometry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@OpenAPIDefinition(
		info = @Info(
				title = "Geometry",
				description = "Test task", version = "1.0.0",
				contact = @Contact(
						name = "Maxim Sukhanov",
						email = "maksimka_13.05.2004@mail.ru"
				)
		)
)
@SpringBootApplication
public class GeometryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeometryApplication.class, args);
	}

	public abstract class QSortMixin{
		@JsonIgnore
		abstract public Sort getOrderSpecifiers();
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder.xml()
				.createXmlMapper(false)
				.build()
				.addMixIn(QSort.class, QSortMixin.class);
	}
}
