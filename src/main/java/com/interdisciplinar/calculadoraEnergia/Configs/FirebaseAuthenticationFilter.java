package com.interdisciplinar.calculadoraEnergia.Configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private final FirebaseTokenService firebaseTokenService;

    public FirebaseAuthenticationFilter(FirebaseTokenService firebaseTokenService) {
        this.firebaseTokenService = firebaseTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtém o token do cabeçalho Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token != null) {
            try {
                // Verifica o token com o Firebase
                var firebaseToken = firebaseTokenService.verifyToken(token);

                // Cria um objeto FirebaseUserDetails com base no UID do token
                var userDetails = new FirebaseUserDetails(firebaseToken.getUid());

                // Cria o token de autenticação do Spring Security
                var auth = new FirebaseAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Coloca o token no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // Limpa o contexto de segurança em caso de erro e retorna 401 Unauthorized
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // Prossegue com a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}