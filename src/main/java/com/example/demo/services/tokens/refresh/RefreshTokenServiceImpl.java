package com.example.demo.services.tokens.refresh;

import com.example.demo.exceptions.jwt.RefreshTokenException;
import com.example.demo.models.token.RefreshToken;
import com.example.demo.repositories.token.RefreshTokenRepository;
import com.example.demo.services.tokens.access.AccessTokenService;
import com.example.demo.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private long RefreshTokenExpiration;
    private RefreshTokenRepository refreshTokenRepository;
    private UserService userService;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   @Qualifier("userProfileService") UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenException("Рефреш-токен не найден"));
    }

    public RefreshToken create(String username) {
        RefreshToken token = new RefreshToken();

        token.setUser(userService.getUserByUsername(username));
        token.setExpiration(new Date(System.currentTimeMillis() + RefreshTokenExpiration));
        token.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(token);
    }

    public boolean isValidExpiration(RefreshToken refreshToken) {
        return refreshToken.getExpiration().after(new Date());
    }

    @Transactional
    public RefreshToken recreate(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
        String username = refreshToken.getUser().getUsername();
        return create(username);
    }

}
