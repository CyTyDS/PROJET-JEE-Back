package com.m2gil.coffeeandcomanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m2gil.coffeeandcomanager.credentials.User;
import com.m2gil.coffeeandcomanager.credentials.UserDTO;

@RestController
@RequestMapping("/user")
public class SpringController {

	@GetMapping()
    public ResponseEntity<UserDTO> getUserDTO(){
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserDTO dto = new UserDTO(user.getUsername(), user.getPassword(), user.getRole());
    	return  new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
    }
	
    @GetMapping("/info")
    public ResponseEntity<User> getUser(){
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return  new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @GetMapping("/name")
    public ResponseEntity<String> getUserName(){
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return  new ResponseEntity<String>(user.getUsername(), HttpStatus.OK);
    }
    
    @GetMapping("/password")
    public ResponseEntity<String> getUserPassword(){
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return  new ResponseEntity<String>(user.getPassword(), HttpStatus.OK);
    }
    
    @GetMapping("/role")
    public ResponseEntity<String> getUserRole(){
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return  new ResponseEntity<String>(user.getRole(), HttpStatus.OK);
    }
}