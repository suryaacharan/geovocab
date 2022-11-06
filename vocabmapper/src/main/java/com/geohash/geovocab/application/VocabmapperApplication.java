package com.geohash.geovocab.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.geohash.geovocab.controller" })
@EntityScan("com.geohash.geovocab.entity")
@EnableJpaRepositories("com.geohash.geovocab.entity")
public class VocabmapperApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VocabmapperApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(VocabmapperApplication.class);
	}

}
