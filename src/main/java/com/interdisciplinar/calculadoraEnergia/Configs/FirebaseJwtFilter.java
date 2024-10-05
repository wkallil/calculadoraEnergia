package com.interdisciplinar.calculadoraEnergia.Configs;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import java.io.IOException;

public class FirebaseJwtFilter extends BasicAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public FirebaseJwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager; // Apenas como exemplo, isso seria configurado no Security Config
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwt);
                // Colocar as informações do usuário no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthenticationToken(decodedToken));
            } catch (FirebaseAuthException e) {
                throw new RuntimeException("Token inválido");
            }
        }

        chain.doFilter(request, response);
    }
}

