package com.interdisciplinar.calculadoraEnergia.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@EnableWebSecurity
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
            "/login",
            "/users/authenticate"
    };

    @Autowired
    private FirebaseTokenService firebaseTokenService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()  // Desabilitar CSRF para API
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Não usar sessão
                .and()
                .authorizeRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll() // Permitir endpoints públicos
                .anyRequest().authenticated() // Proteger os outros endpoints
                .and()
                .addFilterBefore(new FirebaseAuthenticationFilter(firebaseTokenService), AbstractPreAuthenticatedProcessingFilter.class);
        return http.build();
    }

    // Caso precise de um AuthenticationManager:
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }
}
