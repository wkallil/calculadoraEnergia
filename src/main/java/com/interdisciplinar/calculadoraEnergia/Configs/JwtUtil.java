package com.interdisciplinar.calculadoraEnergia.Configs;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Collections;

@Component
public class JwtUtil {

    private static final String JWKS_URL = "https://www.googleapis.com/oauth2/v3/certs";

    // Método para obter a chave pública com base no 'kid' presente no header do JWT
    public RSAPublicKey getPublicKey(String kid) throws IOException, ParseException, JOSEException {
        JWKSet jwkSet = JWKSet.load(new URL(JWKS_URL));
        JWK jwk = jwkSet.getKeyByKeyId(kid);

        // Verificar se a chave é do tipo RSA e convertê-la para RSAPublicKey
        if (jwk instanceof RSAKey) {
            return ((RSAKey) jwk).toRSAPublicKey();
        }

        throw new IllegalArgumentException("Invalid JWK key");
    }

    // Método para validar o JWT e extrair os claims
    public JWTClaimsSet validateAndExtractClaims(String token) throws ParseException, BadJOSEException, IOException, JOSEException {
        // Parse do JWT
        SignedJWT signedJWT = SignedJWT.parse(token);
        String kid = signedJWT.getHeader().getKeyID();  // Pega o 'kid' do header do JWT
        RSAPublicKey publicKey = getPublicKey(kid);

        // Configuração do JWTProcessor para verificar a assinatura
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        // O lambda agora retorna uma lista de chaves, conforme o esperado
        JWSVerificationKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
                signedJWT.getHeader().getAlgorithm(),
                (joseHeader, context) -> Collections.singletonList((JWK) publicKey) // Aqui retornamos uma lista contendo a chave pública
        );

        jwtProcessor.setJWSKeySelector(keySelector);

        // Processar e validar os claims do JWT
        return jwtProcessor.process(signedJWT, null);
    }
}
