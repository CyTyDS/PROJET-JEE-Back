package com.m2gil.coffeeandcomanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m2gil.coffeeandcomanager.credentials.User;
import com.m2gil.coffeeandcomanager.credentials.UserDTO;
import com.m2gil.coffeeandcomanager.repo.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping()
    public ResponseEntity<UserDTO> getUserDTO(){
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserDTO dto = new UserDTO(user.getEmail(), user.getUsername(), user.getPassword(), user.getRole());
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
    
    
    //register
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) throws Exception {
    	// Test admin
    	//TODO role != admin
    	User me = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if (me.getRole().equals("admin")) {
    		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    	}
    	
    	//Test Body
    	if (user == null) {
    		return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
    	}
    	if (user.getEmail() == null 
    			|| user.getEmail().equals("") 
    			|| !user.getEmail().matches("(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)"
    					+ "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")"
    					+ "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}"
    					+ "(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])"
    					+ "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
    		return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
    	}
    	
    	if (user.getUsername() == null 
    			|| user.getUsername().equals("")) {
    		return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
    	}
    	if (user.getPassword() == null 
    			|| user.getPassword().equals("")) {
    		return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
    	}
    	if (user.getRole() == null 
    			|| !user.getRole().equals("admin") 
    			|| !user.getRole().equals("user")) {
    		return new ResponseEntity<String>(HttpStatus.METHOD_NOT_ALLOWED);
    	}
    	
    	// Testing if user already exists
    	if (emailExists(user.getEmail())) {
            throw new Exception(
              "There is an account with that email adress:" + user.getEmail());
        }
        if (nameExists(user.getUsername())) {
            throw new Exception(
              "There is an account with that username:" + user.getUsername());
        }
        
        // Hashing password
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    	userRepository.save(user);
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    
    //OUTILS
    
    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
    
    private boolean nameExists(final String name) {
        return userRepository.findUserWithName(name) != null;
    }
}