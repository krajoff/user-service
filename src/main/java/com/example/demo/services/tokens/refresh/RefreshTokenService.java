package com.example.demo.services.tokens.refresh;

import com.example.demo.models.token.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken findByToken(String token);

    RefreshToken create(String username);

    boolean isValidExpiration(RefreshToken token);
    RefreshToken recreate(RefreshToken refreshToken);
}
