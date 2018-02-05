package com.swk.cpanel.api.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
public class JacksonConfig {
	
	/**
	 * 全局时间格式转换设置
	 * @return
	 */
	@Bean
	@Primary
	public ObjectMapper java8TimeConfig() {
		//时间格式
		DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss");
		//日期格式
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//完整日期时间格式
		DateTimeFormatter dateTimePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    ObjectMapper objectMapper = new ObjectMapper();
	    JavaTimeModule javaTimeModule = new JavaTimeModule();
	    javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timePattern));
	    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(datePattern));
	    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimePattern));
	    javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timePattern));
	    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(datePattern));
	    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimePattern));
	    objectMapper.registerModule(javaTimeModule);
	    return objectMapper;
	}
	
}
