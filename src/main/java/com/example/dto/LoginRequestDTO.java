package com.example.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @NotBlank(message = "please give valid email id")
    private String email;
    @NotBlank(message = "please give password")
    private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    // getters & setters
    
    
}