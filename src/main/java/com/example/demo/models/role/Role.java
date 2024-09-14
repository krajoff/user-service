package com.example.demo.models.role;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;


/**
 * Перечисление, представляющее возможные роли.
 */
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR"),
    USER("ROLE_USER"),
    DELETED("DELETED_ACCOUNT");

    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

}

