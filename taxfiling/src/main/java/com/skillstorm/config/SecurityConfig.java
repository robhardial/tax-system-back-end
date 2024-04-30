package com.skillstorm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorizeHttpRequests -> {
            // all requests coming in require authentication
            authorizeHttpRequests.anyRequest().authenticated();

        })
                .csrf(csrf -> csrf.disable()) // disabling csrf only for demo

                .cors(cors -> {
                    cors.configurationSource(request -> {

                        // configuring how we want to handle cors
                        CorsConfiguration corsConfig = new CorsConfiguration();
                        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                        corsConfig.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
                        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                        corsConfig.setAllowCredentials(true);
                        corsConfig.setMaxAge(3600L);

                        // setting which endpoints to apply the above cors configurations to
                        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                        source.registerCorsConfiguration("/**", corsConfig);

                        return corsConfig;
                    });
                });

        // telling spring security to use our registered OAuth2 client
        httpSecurity.oauth2Login(withDefaults());

        return httpSecurity.build();
    }

}
