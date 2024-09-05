package com.example.demo.services.jwt;

import com.example.demo.exceptions.JwtExpiredException;
import com.example.demo.models.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Сервисный класс для обработки операций JWT (JSON Web Token)
 * таких, как генерация токена, извлечение требований
 * (например, имя пользователя, дата истечения срока действия) и валидация.
 * <p>
 * Эта служба отвечает за:
 * - Генерацию JWT-токенов для аутентифицированных пользователей.
 * - Извлечение из токена определенных утверждений, таких как имя пользователя
 * или дата истечения срока действия.
 * - Проверка токена, чтобы убедиться, что он все еще действителен и соответствует
 * данные пользователя.
 * - Обработка подписания и истечения срока действия токена.
 */
@Service
public class JwtService {
    @Value("${TOKEN_KEY}")
    private String jwtSigningKey;

    @Value("${TOKEN_EXPIRATION}")
    private long jwtExpiration;

    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return тип данных
     */
    private <T> T extractClaim(String token,
                               Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токенов
     *
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("username", customUserDetails.getUsername());
            claims.put("role", customUserDetails.getRole());
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Генерация токенов
     *
     * @param extraClaims дополнительные данные
     * @param userDetails данные пользователя
     * @return токен
     */
    private String generateToken(Map<String, Object>
                                         extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Проверка действительности токена
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return true, если токен действителен
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }

    /**
     * Проверьте токен на истечение срока действия.
     *
     * @param token токен
     * @return true, если срок действия токена истек
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения срока действия токена
     *
     * @param token токен
     * @return дата истечения срока действия
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлеките все данные из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("Token lifetime is over: "
                    + e.getMessage());
        }
    }

    /**
     * Получение ключа для подписи токенов
     *
     * @return key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Получение времени действия токена
     *
     * @return секунды
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }
}
