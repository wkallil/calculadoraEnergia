package com.interdisciplinar.calculadoraEnergia.Configs;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class FirebaseUserDetails implements UserDetails {

    private String uid;

    public FirebaseUserDetails(String uid) {
        this.uid = uid;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Você pode implementar as autoridades (roles) do usuário aqui, se necessário.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // Não usado, já que estamos autenticando via Firebase
        return null;
    }

    @Override
    public String getUsername() {
        return uid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUid() {
        return uid;
    }
}
