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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/users/logout_success").permitAll()
                .anyRequest().authenticated())
                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true)
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("http://team8.skillstorm-congo.com:8080/users/logout_success"));

        http.csrf(csrf -> csrf.disable());

        http.cors(cors -> {
            cors.configurationSource(request -> {
                CorsConfiguration corsConfig = new CorsConfiguration();

                corsConfig.setAllowedOrigins(Arrays.asList("http://team8.skillstorm-congo.com:5173"));
                corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                corsConfig.setAllowCredentials(true);
                corsConfig.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfig);
                return corsConfig;
            });
        });

        // telling spring security to use our registered OAuth2 client
        http.oauth2Login(withDefaults());

        return http.build();
    }

}
