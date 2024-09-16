package com.example.demo.services.cookies;

import com.example.demo.exceptions.jwt.RefreshTokenException;
import com.example.demo.models.token.RefreshToken;
import com.example.demo.payloads.requests.RefreshTokenRequest;
import com.example.demo.payloads.requests.SignInRequest;
import com.example.demo.payloads.requests.SignUpRequest;
import com.example.demo.services.auth.AuthenticationService;
import com.example.demo.services.tokens.access.AccessTokenService;
import com.example.demo.services.tokens.refresh.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class CookieHttpOnlyService {

    private final String accessCookieName = "accessCookie";
    private final String refreshCookieName = "refreshCookie";
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;


    public CookieHttpOnlyService(AuthenticationService authenticationService,
                                 AccessTokenService accessTokenService,
                                 RefreshTokenService refreshTokenService) {
        this.authenticationService = authenticationService;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    public ResponseEntity<?> signUp(SignUpRequest request) {
        var authResponse = authenticationService.signUp(request);
        return getRefreshToken(authResponse.getAccessToken(),
                authResponse.getRefreshToken());
    }


    public ResponseEntity<?> signIn(SignInRequest request) {
        var authResponse = authenticationService.signIn(request);
        return getRefreshToken(authResponse.getAccessToken(),
                authResponse.getRefreshToken());
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();

        if (token != null && !token.isEmpty()) {
            RefreshToken refreshToken = refreshTokenService.findByToken(token);
            refreshToken = refreshTokenService.recreate(refreshToken);
            String accessToken = accessTokenService.generateToken(refreshToken.getUser());
            return getRefreshToken(accessToken, refreshToken.getToken());
        }

        return ResponseEntity.badRequest().body(new RefreshTokenException("Запрос на рефреш-токен пуст."));
    }

    public String getAccessToken(HttpServletRequest request) {
        return getCookieValueByName(request, accessCookieName);
    }

    public String getRefreshToken(HttpServletRequest request) {
        return getCookieValueByName(request, refreshCookieName);
    }

    private ResponseEntity<?> getRefreshToken(String accessToken, String refreshToken) {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, generateAccessCookie(accessToken).toString())
                .header(HttpHeaders.SET_COOKIE, generateRefreshCookie(refreshToken).toString())
                .body("");
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String token = getRefreshToken(request);

        if (token != null && !token.isEmpty()) {
            RefreshToken refreshToken = refreshTokenService.findByToken(token);
            refreshToken = refreshTokenService.recreate(refreshToken);
            String accessToken = accessTokenService.generateToken(refreshToken.getUser());
            return getRefreshToken(accessToken, refreshToken.getToken());
        }
        return ResponseEntity.badRequest().body(new RefreshTokenException("Запрос на рефреш-токен пуст."));
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path)
                .maxAge(24 * 60 * 60).httpOnly(true).build();
    }

    private ResponseCookie generateAccessCookie(String accessToken) {
        return generateCookie(accessCookieName, accessToken, "/api");
    }

    private ResponseCookie generateRefreshCookie(String refreshToken) {
        return generateCookie(refreshCookieName, refreshToken, "/api/refresh/");
    }
}
