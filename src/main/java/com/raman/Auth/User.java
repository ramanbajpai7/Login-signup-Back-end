package com.raman.Auth;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    // getters and setters
    
    public String getUsername() {
		return username;
	}
    
    public String getPassword() {
		return password;
	}
    
    public void setUsername(String username) {
		this.username = username;
	}
    
    public void setPassword(String password) {
		this.password = password;
	}
}