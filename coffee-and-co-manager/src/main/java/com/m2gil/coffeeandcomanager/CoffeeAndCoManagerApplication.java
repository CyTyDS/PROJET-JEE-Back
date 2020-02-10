package com.m2gil.coffeeandcomanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class CoffeeAndCoManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeAndCoManagerApplication.class, args);
	}

}
