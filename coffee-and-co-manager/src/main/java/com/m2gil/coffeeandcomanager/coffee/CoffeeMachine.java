package com.m2gil.coffeeandcomanager.coffee;

import java.util.List;
import java.util.Map;

public class CoffeeMachine {

	private List<Map<String, Integer>> products;
	private String port;
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}

	public List<Map<String, Integer>> getProducts() {
		return products;
	}

	public void setProducts(List<Map<String, Integer>> products) {
		this.products = products;
	}
}
