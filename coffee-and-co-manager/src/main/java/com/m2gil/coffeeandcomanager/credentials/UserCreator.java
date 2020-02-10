package com.m2gil.coffeeandcomanager.credentials;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.m2gil.coffeeandcomanager.repo.UserRepository;

@Component
class UserCreator {
	
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
    	if (userRepository.findByEmail("admin@admin.fr") == null) {
    		System.out.println("Recreated standard admin");
    		User one = new User();
        	one.setEmail("admin@admin.fr");
        	one.setUsername("admin");
        	one.setPassword(new BCryptPasswordEncoder().encode("admin"));
        	one.setRole("admin");
        	userRepository.save(one);
    	}
        
    	if (userRepository.findByEmail("user@user.fr") == null) {
    		System.out.println("Recreated standard user");
    		User two = new User();
        	two.setEmail("user@user.fr");
        	two.setUsername("user");
        	two.setPassword(new BCryptPasswordEncoder().encode("user"));
        	two.setRole("user");
            userRepository.save(two);
    	}
        // etc
    }
}
