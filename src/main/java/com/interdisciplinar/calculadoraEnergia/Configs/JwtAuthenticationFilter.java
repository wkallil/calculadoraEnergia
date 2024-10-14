package com.interdisciplinar.calculadoraEnergia.Configs;

import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);  // Adicionando logger

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.warn("Cabeçalho de autorização ausente ou inválido.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);
        logger.info("Token extraído: " + token);
        try {
            JWTClaimsSet claims = jwtUtil.validateAndExtractClaims(token);
            String email = (String) claims.getClaim("email");
            String userId = (String) claims.getClaim("sub");

            logger.info("JWT Claims - Email: " + email + ", UserID: " + userId);

            if (email != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Usuário autenticado com sucesso: " + email);
            }
        } catch (Exception e) {
            logger.error("Falha na autenticação do token JWT: ", e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}