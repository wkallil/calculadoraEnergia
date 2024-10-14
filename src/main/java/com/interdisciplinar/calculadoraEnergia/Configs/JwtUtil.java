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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Collections;

@Component
public class JwtUtil {

    private static final String JWKS_URL = "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);  // Adicionando logger

    // Método para obter a chave pública com base no 'kid' presente no header do JWT
    public RSAPublicKey getPublicKey(String kid) throws IOException, ParseException, JOSEException {
        logger.info("Recuperando chave pública para o 'kid': " + kid);

        JWKSet jwkSet = JWKSet.load(new URL(JWKS_URL));
        logger.info("JWKSet carregado com sucesso do JWKS_URL.");

        JWK jwk = jwkSet.getKeyByKeyId(kid);
        if (jwk == null) {
            logger.error("Nenhuma chave encontrada para o 'kid': " + kid);
            throw new IllegalArgumentException("Chave não encontrada para o 'kid': " + kid);
        }

        logger.info("Chave JWK encontrada para o 'kid': " + kid);

        // Verificar se a chave é do tipo RSA e convertê-la para RSAPublicKey
        if (jwk instanceof RSAKey) {
            RSAPublicKey publicKey = ((RSAKey) jwk).toRSAPublicKey();
            logger.info("Chave pública RSA obtida com sucesso.");
            return publicKey;
        }

        logger.error("Chave JWK não é do tipo RSA.");
        throw new IllegalArgumentException("Chave JWK inválida");
    }

    // Método para validar o JWT e extrair os claims
    public JWTClaimsSet validateAndExtractClaims(String token) throws ParseException, BadJOSEException, IOException, JOSEException {
        logger.info("Validando e extraindo claims do JWT.");

        // Parse do JWT
        SignedJWT signedJWT = SignedJWT.parse(token);
        String kid = signedJWT.getHeader().getKeyID();
        logger.info("JWT 'kid' extraído: " + kid);

        RSAPublicKey publicKey = getPublicKey(kid);

        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        JWSVerificationKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
                signedJWT.getHeader().getAlgorithm(),
                (joseHeader, context) -> Collections.singletonList((JWK) publicKey)
        );

        jwtProcessor.setJWSKeySelector(keySelector);
        logger.info("JWTProcessor configurado para validação.");

        // Processar e validar os claims do JWT
        return jwtProcessor.process(signedJWT, null);
    }
}