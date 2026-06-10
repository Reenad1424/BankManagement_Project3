package org.example.bankmanagement_project3.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                // قبل اكتب اي باث لازم افتح له باث في الكنترولر قبل
                .authorizeHttpRequests(auth -> auth
                        // فتح مسار التسجيل للجميع للعملاء والموظفين
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register-customer").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register-employee").permitAll()

                        // صلاحيات الـ Admin لإدارة المستخدمين والموظفين والاطلاع الكلي
                        .requestMatchers("/api/v1/auth/get-users").hasAuthority("ADMIN")

                        // صلاحيات الـ Customer لإنشاء حساباتهم والعمليات المالية الخاصة بهم
                        .requestMatchers("/api/v1/account/create").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/account/my-accounts").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/account/deposit/**", "/api/v1/account/withdraw/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/account/transfer/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/account/view-my-account/{id}").hasAuthority("CUSTOMER")

                        // صلاحيات الـ Employee والـ Admin لتفعيل الحسابات أو حظرها والاطلاع العام
                        .requestMatchers("/api/v1/account/activate/{id}").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/api/v1/account/block/{id}").hasAnyAuthority("EMPLOYEE", "ADMIN")
                        .requestMatchers("/api/v1/account/get-all").hasAnyAuthority("EMPLOYEE", "ADMIN")

                        .anyRequest().authenticated() // يعني اي باث مو موجود فوق بالماتشر يسوي لي ان اوثريازد ولا يشغله
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
