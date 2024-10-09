package com.interdisciplinar.calculadoraEnergia.Configs;

import com.google.firebase.auth.FirebaseToken;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {
    private final FirebaseToken firebaseToken;
    @Getter
    private final String uid;

    public FirebaseAuthenticationToken(FirebaseToken firebaseToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority("USER"))); // você pode ajustar as permissões aqui
        this.firebaseToken = firebaseToken;
        this.uid = firebaseToken.getUid();
        setAuthenticated(true); // marca o token como autenticado
    }

    @Override
    public Object getCredentials() {
        return null; // O token já foi validado pelo Firebase, não há necessidade de credenciais adicionais.
    }

    @Override
    public Object getPrincipal() {
        return this.firebaseToken;
    }

}

