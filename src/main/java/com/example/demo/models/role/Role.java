package com.example.demo.models.role;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;


/**
 * Перечисление, представляющее возможные роли.
 */
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_USER("ROLE_USER"),
    DELETED_ACCOUNT("DELETED_ACCOUNT");

    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

}

