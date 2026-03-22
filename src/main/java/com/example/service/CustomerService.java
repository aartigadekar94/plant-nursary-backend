package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CustomerDao;
import com.example.dao.UserDao;
import com.example.dto.CustomerResponseDTO;
import com.example.dto.UserResponseDTO;
import com.example.entity.Customer;
import com.example.entity.Role;
import com.example.entity.User;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerRepository;

    @Autowired
    private UserDao userRepo;
    
    
   
    public CustomerResponseDTO convertToCustomerResponse(Customer customer)
    {
    	
    	//Customer cust = customerRepository.findByUserEmail(email).get();
    	CustomerResponseDTO custDTO=new CustomerResponseDTO();
    	custDTO.setEmail(customer.getUser().getEmail());
    	custDTO.setName(customer.getName());
    	custDTO.setAddress(customer.getAddress());
    	custDTO.setPhoneNumber(customer.getPhoneNumber());
    	custDTO.setId(customer.getId());
    	return custDTO;
    	
    	
    	
    }

    public UserResponseDTO getCurrentuser(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO dto = new UserResponseDTO();
        dto.setEmail(user.getEmail());

        // 🔥 If ADMIN
        if (user.getRole()==Role.ADMIN) {

            dto.setName("Admin");
            dto.setAddress("N/A");
            dto.setPhoneNumber("N/A");
            dto.setId(user.getId());
            dto.setRole(user.getRole().name());

        }
        // 🔥 If CUSTOMER
        else if (user.getRole()== Role.CUSTOMER) {

            Customer customer = customerRepository
                    .findByUserEmail(email)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            dto.setName(customer.getName());
            dto.setAddress(customer.getAddress());
            dto.setPhoneNumber(customer.getPhoneNumber());
            dto.setId(customer.getId());
            dto.setRole(customer.getUser().getRole().name());
        }

        return dto;
    }

    public UserResponseDTO updateCustomer(String email, Customer updateDTO) {

        Customer customer = customerRepository
                .findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(updateDTO.getName());
        customer.setAddress(updateDTO.getAddress());
        customer.setPhoneNumber(updateDTO.getPhoneNumber());

        customerRepository.save(customer);

        return getCurrentuser(email);
    }
    
    
    public List<CustomerResponseDTO> getAllCustomer()
    {
    	List<Customer> allCust = customerRepository.findAll();
    	 return allCust.stream()
                 .map(this::convertToCustomerResponse)
                 .toList();
    	
    }

    public void deleteCustomer(String email) {
        Customer customer = customerRepository
                .findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerRepository.delete(customer);
    }
}


