package com.example.demo.services.tokens.refresh;

import com.example.demo.models.token.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken create(String username);

    RefreshToken verifyExpiration(RefreshToken token);

}
