package com.interdisciplinar.calculadoraEnergia.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseTokenService firebaseTokenService;

    @Autowired
    public SecurityConfig(FirebaseTokenService firebaseTokenService) {
        this.firebaseTokenService = firebaseTokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Habilita CORS
                .and()
                .csrf().disable() // Desabilita CSRF para permitir chamadas da API
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/login", "/v2/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(firebaseAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public FirebaseAuthenticationFilter firebaseAuthenticationFilter() {
        return new FirebaseAuthenticationFilter(firebaseTokenService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Permitir todas as origens (domínios). Modifique para restringir conforme necessário.
        configuration.addAllowedMethod("*"); // Permitir todos os métodos (GET, POST, etc.)
        configuration.addAllowedHeader("*"); // Permitir todos os headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
