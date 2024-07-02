package com.crio.stayease.security;

import com.crio.stayease.security.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public AppSecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/admin/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hotels").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hotels/*").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
