package com.interdisciplinar.calculadoraEnergia.Configs;

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
    @Value("${firebase.public.key}")
    private String firebasePublicKeyUrl;

    public RSAPublicKey getPublicKey() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> keys = restTemplate.getForObject(firebasePublicKeyUrl, Map.class);

        // Aqui você pode escolher a chave que deseja usar
        // Normalmente, o Firebase fornece várias chaves. Você pode precisar escolher a correta com base no 'kid' do token.
        String publicKeyPEM = keys.values().iterator().next(); // Pega a primeira chave como exemplo
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
    }
}
