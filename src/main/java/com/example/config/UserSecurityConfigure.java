package com.example.config;



import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class UserSecurityConfigure {
	
//		 @Bean
//		 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		     http
//		         .csrf(csrf -> csrf.disable())
//		         .cors(cors -> {})   // ✅ VERY IMPORTANT
//		         .authorizeHttpRequests(auth -> auth
//		        	 .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ⭐ allow preflight
//		             .requestMatchers("/api/auth/**").permitAll()
//		             .requestMatchers("/api/products/admin/**").permitAll() // testing
//		             .anyRequest().authenticated()
//		         )
//		         .httpBasic(httpBasic -> httpBasic.disable())  // ✅ disable popup
//		         .formLogin(form -> form.disable());           // ✅ disable form login
//
//		     return http.build();
//		
//	}
	@Bean
	SecurityFilterChain security(HttpSecurity http) throws Exception {

	    http
	        .cors(Customizer.withDefaults())   // ✅ VERY IMPORTANT
	        .csrf(csrf -> csrf.disable())

	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // ⭐ allow preflight
	            .requestMatchers("/api/auth/**").permitAll()
	            .requestMatchers("/login", "/logout").permitAll()
	            .requestMatchers("/api/products").permitAll()
	            .requestMatchers("/api/customer/**").authenticated()
	            .requestMatchers("/orders/**").authenticated()
	            .requestMatchers("/api/auth/me").authenticated()
	            .requestMatchers("/cart/**").authenticated()
	            .requestMatchers("/api/products/admin/**").hasRole("ADMIN")
		       // .requestMatchers("/orders/**").hasRole("CUSTOMER")
		        .requestMatchers("/api/admin/all").hasRole("ADMIN")
		        .requestMatchers("/orders/all").hasRole("ADMIN")
	            .anyRequest().permitAll()
	        )

	        .formLogin(form -> form
	            .loginProcessingUrl("/login")
	            .successHandler((req, res, auth) -> {
	                res.setStatus(200);
	            })
	            .failureHandler((req, res, ex) -> {
	                res.setStatus(401);
	            })
	        )

	        .exceptionHandling(ex -> ex
	            .authenticationEntryPoint((req, res, ex2) -> {
	                res.setStatus(401);  // ✅ instead of redirect
	            })
	        )

	        .logout(logout -> logout
	                .logoutUrl("/logout")   // logout endpoint
	                .logoutSuccessHandler((request, response, authentication) -> {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                })
	                .invalidateHttpSession(true)     // destroy session
	                .deleteCookies("JSESSIONID"));  

	    return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

}
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173",
        		"https://majestic-bublanina-fecbfe.netlify.app"));//add netlyfly url
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    
    
    }
}
