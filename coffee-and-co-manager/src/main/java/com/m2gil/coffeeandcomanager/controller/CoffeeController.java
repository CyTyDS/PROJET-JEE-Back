package com.m2gil.coffeeandcomanager.controller;

import com.m2gil.coffeeandcomanager.coffee.CoffeeMachine;
import com.m2gil.coffeeandcomanager.credentials.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class CoffeeController {

	private Map<String, CoffeeMachine> coffeeMap = new HashMap<String, CoffeeMachine>();
	
	@GetMapping()
    public ResponseEntity<List<CoffeeMachine>> getAllMachines(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if (user == null || ! user.getRole().equals("admin")) {
    		return new ResponseEntity<List<CoffeeMachine>>(HttpStatus.FORBIDDEN);
    	}
		
    	return new ResponseEntity<List<CoffeeMachine>>(new LinkedList<CoffeeMachine>(coffeeMap.values()), HttpStatus.OK);
    }
	
	
	// #####################  Machine #####################
	
	@PostMapping()
    public ResponseEntity<String> addMachine(@RequestHeader("host") String host, @RequestBody CoffeeMachine cm){
		// Verifications (le parsing Jackson ne casse pas l'application, les valeurs négatives sont remplacées par 0)
		for (Map<String, Integer> m : cm.getProducts()) {
			for (String s : m.keySet()) {
				if (m.get(s) < 0) {
					m.put(s, 0);
				}
			}
		}
		
    	coffeeMap.put(host, cm);
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	
	// ################### CRUD Produits ###################
	
	// Ajout d'un objet sur une machine identifiée par son port
	@PostMapping("/addProduct/{productName}/to/{nomMachine}")
    public ResponseEntity<String> addProduct(@PathVariable String productName, @PathVariable String nomMachine) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if (user == null || ! user.getRole().equals("admin")) {
    		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    	}
		
		String url = "http://" + nomMachine + "/product";

	    // create a string for post parameters
	    String body = "{ \"productName\":\"" + productName + "\" }";

	    // build the request
	    HttpEntity<String> entity = new HttpEntity<>(body);

	    // send POST request
	    ResponseEntity<String> response = new RestTemplateBuilder().build().postForEntity(url, entity, String.class);
		
	    if (response.getStatusCode() != HttpStatus.OK) {
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	    }
	    
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	// Suppression d'un objet sur une machine identifiée par son port
	@PostMapping("/removeProduct/{productName}/to/{nomMachine}")
    public ResponseEntity<String> removeProduct(@PathVariable String productName, @PathVariable String nomMachine) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if (user == null || ! user.getRole().equals("admin")) {
    		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    	}
		
		String url = "http://" + nomMachine + "/product";

	    // create a string for post parameters
	    String body = "{ \"productName\":\"" + productName + "\" }";

	    // build the request
	    HttpEntity<String> entity = new HttpEntity<>(body);

	    // send POST request
	    ResponseEntity<String> response = new RestTemplateBuilder().build().exchange(url, HttpMethod.DELETE, entity, String.class);
		
	    if (response.getStatusCode() != HttpStatus.OK) {
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	    }
	    
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	// Ajout d'une quantité d'un objet sur une machine identifiée par son port
	@PostMapping("/addProduct/{productName}/to/{nomMachine}/quantity/{qty}")
    public ResponseEntity<String> addManyProduct(@PathVariable String productName, @PathVariable String nomMachine, @PathVariable int qty) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if (user == null || ! user.getRole().equals("admin")) {
    		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    	}
		
		String url = "http://" + nomMachine + "/product";

	    // create a string for post parameters
	    String body = "{ \"productName\":\"" + productName + "\", \"howMany\":" + qty + " }";

	    // build the request
	    HttpEntity<String> entity = new HttpEntity<>(body);

	    // send POST request
	    ResponseEntity<String> response = new RestTemplateBuilder().build().postForEntity(url, entity, String.class);
		
	    if (response.getStatusCode() != HttpStatus.OK) {
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	    }
	    
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	// Suppression d'une quantité d'un objet sur une machine identifiée par son port
	@PostMapping("/removeProduct/{productName}/to/{nomMachine}/quantity/{qty}")
    public ResponseEntity<String> removeManyProduct(@PathVariable String productName, @PathVariable String nomMachine, @PathVariable int qty) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if (user == null || ! user.getRole().equals("admin")) {
    		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    	}
		
		String url = "http://" + nomMachine + "/product";

	    // create a string for post parameters
	    String body = "{ \"productName\":\"" + productName + "\", \"howMany\":" + qty + " }";

	    // build the request
	    HttpEntity<String> entity = new HttpEntity<>(body);

	    // send POST request
	    ResponseEntity<String> response = new RestTemplateBuilder().build().exchange(url, HttpMethod.DELETE, entity, String.class);
		
	    if (response.getStatusCode() != HttpStatus.OK) {
	    	return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	    }
	    
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
}
