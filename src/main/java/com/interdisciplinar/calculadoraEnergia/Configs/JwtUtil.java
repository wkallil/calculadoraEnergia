package com.interdisciplinar.calculadoraEnergia.Configs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Collections;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String FIREBASE_CERTS_URL = "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public RSAPublicKey getPublicKey(String kid) throws IOException, CertificateException {
        logger.info("Recuperando chave pública para o 'kid': " + kid);

        // Carregar o conjunto de certificados X.509 do Firebase
        URL url = new URL(FIREBASE_CERTS_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // Converter a resposta JSON em um Map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> certs = mapper.readValue(response.toString(), new TypeReference<Map<String, String>>() {});

            // Obter o certificado X.509 correspondente ao 'kid'
            String certString = certs.get(kid);
            if (certString == null) {
                logger.error("Nenhum certificado encontrado para o 'kid': " + kid);
                throw new IllegalArgumentException("Certificado não encontrado para o 'kid': " + kid);
            }

            // Converter o certificado X.509 em RSAPublicKey
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            InputStream certStream = new ByteArrayInputStream(certString.getBytes(StandardCharsets.UTF_8));
            X509Certificate cert = (X509Certificate) fact.generateCertificate(certStream);
            RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();

            logger.info("Chave pública RSA obtida com sucesso.");
            return publicKey;
        }
    }

    public JWTClaimsSet validateAndExtractClaims(String token) throws ParseException, BadJOSEException, JOSEException, IOException, CertificateException {
        logger.info("Validando e extraindo claims do JWT.");

        // Parsear o token JWT
        SignedJWT signedJWT = SignedJWT.parse(token);
        String kid = signedJWT.getHeader().getKeyID();
        logger.info("JWT 'kid' extraído: " + kid);

        // Obter a chave pública para o 'kid'
        RSAPublicKey publicKey = getPublicKey(kid);

        // Criar um verificador RSA com a chave pública
        JWSVerifier verifier = new RSASSAVerifier(publicKey);

        // Verificar a assinatura
        if (!signedJWT.verify(verifier)) {
            throw new BadJOSEException("Assinatura JWT inválida.");
        }

        logger.info("JWT verificado com sucesso.");

        // Retornar os claims após a verificação bem-sucedida
        return signedJWT.getJWTClaimsSet();
    }
}