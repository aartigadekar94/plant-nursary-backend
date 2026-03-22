package com.example.service;

import java.util.List;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.entity.User;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserDao userRepository;

    public CustomUserDetailService(UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword()) // already encoded
                .authorities(
                        List.of(
                            new SimpleGrantedAuthority(
                                    "ROLE_" + user.getRole().name()
                            )
                        )
                )
                .build();
    }
}