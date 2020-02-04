package com.m2gil.coffeeandcomanager.controller;

import com.m2gil.coffeeandcomanager.coffee.CoffeeMachine;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class CoffeeController {

	private Map<String, CoffeeMachine> coffeeMap = new HashMap<String, CoffeeMachine>();
	private List<String> blacklist = new LinkedList<String>();
	
	@GetMapping()
    public ResponseEntity<List<CoffeeMachine>> getAllMachines(){
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	if (! user.getRole().equals("admin")) {
//    		return new ResponseEntity<List<CoffeeMachine>>(HttpStatus.FORBIDDEN);
//    	}
		
    	return new ResponseEntity<List<CoffeeMachine>>(new LinkedList<CoffeeMachine>(coffeeMap.values()), HttpStatus.OK);
    }
	
	
	
	// #####################  Machine #####################
	
	@PostMapping()
    public ResponseEntity<String> addMachine(@RequestBody CoffeeMachine cm, HttpServletRequest request){
		// Verifications (le parsing Jackson ne casse pas l'application, les valeurs négatives sont remplacées par 0)
		for (Map<String, Integer> m : cm.getProducts()) {
			for (String s : m.keySet()) {
				if (m.get(s) < 0) {
					m.put(s, 0);
				}
			}
		}
		
		String host = request.getRemoteAddr() + ":" + cm.getPort();
	    System.out.println(host);
	    
		if (host == null || host.equals("")) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		
		if (this.blacklist.contains(host)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		
		cm.setPort(host);
		
    	coffeeMap.put(host, cm);
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	// Remove machine
	@DeleteMapping()
    public ResponseEntity<String> blacklistMachine(@RequestBody String machineName){
		if (this.blacklist.contains(machineName)) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		System.out.println(machineName);
		if (this.coffeeMap.get(machineName) == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		this.blacklist.add(machineName);
		
    	coffeeMap.remove(machineName);
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	// ################### CRUD Produits ###################
	
	// Ajout d'un objet sur une machine identifiée par son port
	@PostMapping("/addProduct/{productName}/to/{nomMachine}")
    public ResponseEntity<String> addProduct(@PathVariable String productName, @PathVariable String nomMachine) {
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
