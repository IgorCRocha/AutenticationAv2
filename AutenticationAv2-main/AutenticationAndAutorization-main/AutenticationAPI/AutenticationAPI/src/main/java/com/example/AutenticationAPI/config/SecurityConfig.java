package com.example.AutenticationAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/username/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/gerente/**").hasRole("GERENTE")
                        .requestMatchers("/vendedor/**").hasRole("VENDEDOR")
                        .requestMatchers("/cliente/**").hasRole("CLIENTE")
                        .anyRequest()
                        .authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }
//usuário cadastrados para utilização da API
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("nathy")
                .password(passwordEncoder().encode("5678"))
                .roles("ADMIN")
                .build();
        UserDetails gerente = User.builder()
                .username("igor")
                .password(passwordEncoder().encode("1234"))
                .roles( "GERENTE")
                .build();
        UserDetails vendedor = User.builder()
                .username("joaquim")
                .password(passwordEncoder().encode("12345"))
                .roles( "VENDEDOR")
                .build();

        UserDetails cliente = User.builder()
                .username("bela")
                .password(passwordEncoder().encode("12345"))
                .roles( "cliente")
                .build();
        return new InMemoryUserDetailsManager(user,gerente, vendedor,cliente);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
