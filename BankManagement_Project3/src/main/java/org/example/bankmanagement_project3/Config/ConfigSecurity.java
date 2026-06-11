package org.example.bankmanagement_project3.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(auth -> auth
                        
                        .requestMatchers("/api/v1/auth/register-customer", "/api/v1/auth/register-employee").permitAll()

                        .requestMatchers("/api/v1/account/create", "/api/v1/account/my-accounts", "/api/v1/account/view-my-account/{id}", "/api/v1/account/deposit/**", "/api/v1/account/withdraw/**", "/api/v1/account/transfer/**").hasAuthority("CUSTOMER")

                        .requestMatchers("/api/v1/account/activate/{id}", "/api/v1/account/block/{id}", "/api/v1/account/get-all").hasAnyAuthority("EMPLOYEE", "ADMIN")

                        .requestMatchers("/api/v1/auth/get-users").hasAuthority("ADMIN")

                        .anyRequest().authenticated() 
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }}


