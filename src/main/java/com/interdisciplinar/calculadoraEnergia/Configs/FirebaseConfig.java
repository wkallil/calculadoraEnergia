package com.interdisciplinar.calculadoraEnergia.Configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp initializeFirebaseApp() {
        try {
            String firebaseConfig = "{"
                    + "\"type\": \"" + System.getenv("FIREBASE_TYPE") + "\","
                    + "\"project_id\": \"" + System.getenv("FIREBASE_PROJECT_ID") + "\","
                    + "\"private_key_id\": \"" + System.getenv("FIREBASE_PRIVATE_KEY_ID") + "\","
                    + "\"private_key\": \"" + System.getenv("FIREBASE_PRIVATE_KEY").replace("\\n", "\n") + "\","
                    + "\"client_email\": \"" + System.getenv("FIREBASE_CLIENT_EMAIL") + "\","
                    + "\"client_id\": \"" + System.getenv("FIREBASE_CLIENT_ID") + "\","
                    + "\"auth_uri\": \"" + System.getenv("FIREBASE_AUTH_URI") + "\","
                    + "\"token_uri\": \"" + System.getenv("FIREBASE_TOKEN_URI") + "\","
                    + "\"auth_provider_x509_cert_url\": \"" + System.getenv("FIREBASE_AUTH_PROVIDER_X509_CERT_URL") + "\","
                    + "\"client_x509_cert_url\": \"" + System.getenv("FIREBASE_CLIENT_X509_CERT_URL") + "\","
                    + "\"universe_domain\": \"" + System.getenv("FIREBASE_UNIVERSE_DOMAIN") + "\""
                    + "}";

            ByteArrayInputStream serviceAccountStream = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .build();

            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase app: " + e.getMessage(), e);
        }
    }
}

