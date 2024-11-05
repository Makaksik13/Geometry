package com.sukhanov.geometry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

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
