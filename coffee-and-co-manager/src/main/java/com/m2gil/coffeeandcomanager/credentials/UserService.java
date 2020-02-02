package com.m2gil.coffeeandcomanager.credentials;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.m2gil.coffeeandcomanager.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // WE FIND BY EMAIL HERE
    // UserDetailsService define loadByUsername, that's why we use this
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Objects.requireNonNull(email);
        User user = userRepository.findUserWithName(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }

    //NEW
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerNewUserAccount(UserDTO accountDto) throws Exception {
        if (emailExists(accountDto.getEmail())) {
            throw new Exception(
              "There is an account with that email adress:" + accountDto.getEmail());
        }
        User user = new User();
        user.setUsername(accountDto.getUsername());
         
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
         
        user.setEmail(accountDto.getEmail());
        user.setRole(accountDto.getRole());
        return userRepository.save(user);
    }
    
    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
}
