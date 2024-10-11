package com.interdisciplinar.calculadoraEnergia.Configs;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class FirebaseKeyProvider {
    private static final String FIREBASE_PUBLIC_KEY_URL = "${firebase.public.key}";

    public RSAPublicKey getPublicKey() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> keys = restTemplate.getForObject(FIREBASE_PUBLIC_KEY_URL, Map.class);

        // Aqui você pode escolher a chave que deseja usar
        // Normalmente, o Firebase fornece várias chaves. Você pode precisar escolher a correta com base no 'kid' do token.
        String publicKeyPEM = keys.values().iterator().next(); // Pega a primeira chave como exemplo
        String publicKeyBase64 = publicKeyPEM.replace("-----BEGIN CERTIFICATE-----", "")
                .replace("-----END CERTIFICATE-----", "")
                .replaceAll("\\s+", "");

        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
}
