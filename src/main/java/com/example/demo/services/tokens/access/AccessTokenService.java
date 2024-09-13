package com.example.demo.services.tokens.access;

import com.example.demo.models.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
@Getter
@Slf4j
public class AccessTokenService {

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${ACCESS_TOKEN_EXPIRATION}")
    private long accessTokenExpiration;

    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return username имя пользователя
     */
    public String getUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токенов
     *
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails) {
        var claims = generateClaims(userDetails);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getAccessTokenExpiration()))
                .signWith(getJwtKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @param userDetails данные пользователя
     * @return необходимые утверждения, связанные с пользователем
     */
    private Map<String, Object> generateClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("username", customUserDetails.getUsername());
            claims.put("role", customUserDetails.getRole());
        }
        return claims;
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
        final Claims claims = extractClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Проверка действительности токена
     *
     * @param token токен
     * @return true, если токен действителен
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getJwtKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Время жизни токена истекло", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Неподдерживаемый тип токена", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Некорректно сформированный токен", mjEx);
        } catch (Exception e) {
            log.error("Невалидный токен", e);
        }
        return false;
    }

    /**
     * Проверка токена на истечение срока действия.
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
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getJwtKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получение ключа для подписи токенов
     *
     * @return Key ключ для подписи токенов
     */
    private Key getJwtKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
