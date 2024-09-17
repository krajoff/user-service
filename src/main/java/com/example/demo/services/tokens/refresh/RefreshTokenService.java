package com.example.demo.services.tokens.refresh;

import com.example.demo.models.token.RefreshToken;
import com.example.demo.models.user.User;

public interface RefreshTokenService {
    RefreshToken findByToken(String token);

    RefreshToken findByUserId(Long id);

    RefreshToken create(User user);

    RefreshToken generate(User user);

    boolean isValidExpiration(RefreshToken refreshToken);

    RefreshToken update(String token);

    RefreshToken update(User user);

    void deleteAll();

    void deleteByUserId(Long id);
}
