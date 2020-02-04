package com.m2gil.coffeeandcomanager.controller;

import com.m2gil.coffeeandcomanager.coffee.CoffeeMachine;
import com.m2gil.coffeeandcomanager.credentials.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class CoffeeController {

	private Map<Integer, CoffeeMachine> coffeeMap = new HashMap<Integer, CoffeeMachine>();
	
	@GetMapping()
    public ResponseEntity<List<CoffeeMachine>> getAllMachines(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if (user == null || ! user.getRole().equals("admin")) {
    		return new ResponseEntity<List<CoffeeMachine>>(HttpStatus.FORBIDDEN);
    	}
		
    	return  new ResponseEntity<List<CoffeeMachine>>(new LinkedList<CoffeeMachine>(coffeeMap.values()), HttpStatus.OK);
    }
	
	@PostMapping()
    public ResponseEntity<String> addMachine(@RequestBody CoffeeMachine cm){
		// Verifications (le parsing Jackson ne casse pas l'application, les valeurs négatives sont remplacées par 0)
		for (Map<String, Integer> m : cm.getProducts()) {
			for (String s : m.keySet()) {
				if (m.get(s) < 0) {
					m.put(s, 0);
				}
			}
		}
		
    	coffeeMap.put(cm.getPort(), cm);
    	return  new ResponseEntity<String>(HttpStatus.OK);
    }
	
	// TODO
//	@PostMapping("/addProduct")
//    public ResponseEntity<String> addProduct(@RequestBody CoffeeMachine cm){
//		// Verifications (le parsing Jackson ne casse pas l'application, les valeurs négatives sont remplacées par 0)
//		for (Map<String, Integer> m : cm.getProducts()) {
//			for (String s : m.keySet()) {
//				if (m.get(s) < 0) {
//					m.put(s, 0);
//				}
//			}
//		}
//		
//    	coffeeMap.put(cm.getPort(), cm);
//    	return  new ResponseEntity<String>(HttpStatus.OK);
//    }
}
