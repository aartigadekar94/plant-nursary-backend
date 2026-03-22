package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignUpRequestDTO {
    @NotBlank(message = "name is required")
    private String name;
    
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
//    @Pattern(
//        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
//        message = "Password must be 8+ chars, include uppercase, lowercase, number and special character"
//    )
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
    @NotBlank
    private String address;

    @NotBlank(message = "Phone is required")
//    @Pattern(
//        regexp = "^[6-9]\\d{9}$",
//        message = "Phone number must be 10 digits and start with 6-9"
//    )
    @Size(min = 10, max = 10, message = "phone number must be  digits")
    private String phoneNumber;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    // getters & setters
    
    
}
