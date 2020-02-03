package com.m2gil.coffeeandcomanager.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.m2gil.coffeeandcomanager.credentials.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
    @Query(" select u from User u " +
            " where u.email = ?1")
    Optional<User> findUserWithEmail(String email);
    
    @Query(" select u from User u " +
            " where u.username = ?1")
    Optional<User> findUserWithName(String name);

	User findByEmail(String email);
}
