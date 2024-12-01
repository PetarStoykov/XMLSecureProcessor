package com.petarstoykov.xmlsecureprocessor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/books/**").hasRole("VIEW")
                        .anyRequest().permitAll())
                    .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails mainUser = User.builder()
                .username("Petar")
                .password(passwordEncoder().encode("petarpass"))
                .roles("VIEW")
                .build();

        UserDetails unauthorizedUser = User.builder()
                .username("Ivan")
                .password(passwordEncoder().encode("ivanpass"))
                .roles("VIEW")
                .build();

        return new InMemoryUserDetailsManager(mainUser, unauthorizedUser);
    }
}
