package com.reactbooks.spring_boot_library.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import java.security.interfaces.RSAPublicKey;

@Configuration
public class SecurityConfiguration {

    private RSAPublicKey key;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        // Disable Cross Site Request Forgery
        http.csrf((csrf) -> csrf.disable());

        // Protect endpoints at /api/<type>/secure
        http.authorizeHttpRequests((configurer) ->
                configurer.requestMatchers("/api/books/secure/**",
                        "/api/reviews/secure/**",
                        "/api/messages/secure/**",
                        "/api/admin/secure/**")
                        .authenticated()

        )
                .oauth2ResourceServer((oauth2ResourceServer) ->
                        oauth2ResourceServer.jwt((jwt) ->
                                jwt.decoder(jwtDecoder())));

        http.cors(Customizer.withDefaults());

        // Add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401s to make the response friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }
}
