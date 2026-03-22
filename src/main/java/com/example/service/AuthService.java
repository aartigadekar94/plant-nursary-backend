package com.example.service;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.CustomerDao;
import com.example.dao.UserDao;
import com.example.dto.CartResponseDTO;
import com.example.dto.CustomerResponseDTO;
import com.example.dto.LoginRequestDTO;
import com.example.dto.SignUpRequestDTO;

import com.example.entity.Customer;
import com.example.entity.Role;
import com.example.entity.User;

@Service
public class AuthService {

    private final CustomerDao customerRepository;
    private final UserDao userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(CustomerDao customerRepository,
                           UserDao userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // =========================
    // SIGNUP
    // =========================
    public CustomerResponseDTO register(SignUpRequestDTO dto) {

        // Create User
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        //password encoder encrypt the password so real password dont saved in db  ex-1234  save
        //like $2a$10$7KfHk9w3jX....so if db hacked real password is safe
        
        user.setRole(Role.CUSTOMER);//manually setting role in db

        User savedUser = userRepository.save(user);

        // Create Customer
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(savedUser);

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponseDTO(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedUser.getEmail(),
                savedCustomer.getAddress(),
                savedCustomer.getPhoneNumber()
                );
    }

    // =========================
    // LOGIN
    // =========================
    public String login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        return "Login Successful";
    }
    
    
}