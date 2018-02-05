package com.swk.cpanel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.spring4all.swagger.EnableSwagger2Doc;

@EnableMongoAuditing
@SpringBootApplication
@EnableSwagger2Doc
@EnableConfigurationProperties
public class CpanelApiBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpanelApiBaseApplication.class, args);
	}
}
