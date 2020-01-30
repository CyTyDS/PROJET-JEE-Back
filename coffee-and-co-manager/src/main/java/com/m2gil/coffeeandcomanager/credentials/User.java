package com.m2gil.coffeeandcomanager.credentials;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "CONNEXION")
public class User implements Serializable , UserDetails {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String role;
    
    public Integer getUserId() {
        return id;
    }
    
    public void setUserId(Integer userId) {
        this.id = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
