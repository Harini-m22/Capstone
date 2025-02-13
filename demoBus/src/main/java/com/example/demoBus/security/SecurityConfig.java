package com.example.demoBus.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (since we're using stateless JWT authentication)
                .csrf(csrf -> csrf.disable())
                // Use the new lambda DSL for authorizing HTTP requests

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(" /dashboard","/error","/login","/busRoutes/**","/booking**","/favicon.ico", "/search**","/register", "/api/login", "/api/register","searchRoutes**").permitAll()
                        .anyRequest().authenticated()
                )
                // Set session management to stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );


        // Add the JWT filter so that it runs before Spring Security’s authentication.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
