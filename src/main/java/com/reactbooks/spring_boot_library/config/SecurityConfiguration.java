package com.reactbooks.spring_boot_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

//    Configures the security filter chain, which includes authorization rules,
//    OAuth2 resource server configuration, and CORS settings.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        (req) -> req.requestMatchers("/api/books/secure/**",
                        "/api/reviews/secure/**",
                        "/api/messages/secure/**",
                        "/api/admin/secure/**").authenticated()
                                .requestMatchers("/**").permitAll()
                )
                .oauth2ResourceServer((srv) -> srv.jwt(Customizer.withDefaults()))
                .cors(Customizer.withDefaults())
                .build();
    }
}