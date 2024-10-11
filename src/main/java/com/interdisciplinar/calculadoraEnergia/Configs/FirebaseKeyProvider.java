package com.interdisciplinar.calculadoraEnergia.Configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class FirebaseKeyProvider {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseKeyProvider.class);

    @Value("${firebase.public.key}")
    private String firebasePublicKeyUrl;

    public RSAPublicKey getPublicKey() throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> keys = restTemplate.getForObject(firebasePublicKeyUrl, Map.class);

            // Pega a primeira chave como exemplo
            String publicKeyPEM = keys.values().iterator().next();
            String publicKeyBase64 = publicKeyPEM
                    .replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll("\\s+", "");

            byte[] decoded = Base64.getDecoder().decode(publicKeyBase64);

            // Use CertificateFactory para converter o certificado X509
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(decoded));

            // Extraímos a chave pública do certificado X509
            RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();

            return publicKey;
        } catch (Exception e) {
            logger.error("Erro ao obter a chave pública do Firebase: {}", e.getMessage());
            throw new Exception("Erro ao obter a chave pública do Firebase", e);
        }
    }
}
