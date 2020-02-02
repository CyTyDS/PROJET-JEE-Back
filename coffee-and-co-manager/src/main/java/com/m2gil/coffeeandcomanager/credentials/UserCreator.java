package com.m2gil.coffeeandcomanager.credentials;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.m2gil.coffeeandcomanager.repo.UserRepository;

@Component
class UserCreator {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
    	User one = new User();
    	one.setEmail("admin@admin.fr");
    	one.setUsername("admin");
    	one.setPassword("admin");
    	one.setRole("admin");
        userRepository.save(one);
        
        User two = new User();
    	two.setEmail("user@user.fr");
    	two.setUsername("user");
    	two.setPassword("user");
    	two.setRole("user");
        userRepository.save(two);
        // etc
    }
}
