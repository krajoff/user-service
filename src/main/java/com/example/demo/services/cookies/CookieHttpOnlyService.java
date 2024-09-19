package com.example.demo.services.cookies;

import com.example.demo.payloads.requests.RefreshTokenRequest;
import com.example.demo.payloads.requests.SignInRequest;
import com.example.demo.payloads.requests.SignUpRequest;
import com.example.demo.services.auth.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class CookieHttpOnlyService {

    private final String accessCookieName = "accessToken";
    private final String refreshCookieName = "refreshToken";
    private final AuthenticationService authenticationService;

    public CookieHttpOnlyService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<?> signUp(SignUpRequest request) {
        var authResponse = authenticationService.signUp(request);
        return getTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
    }


    public ResponseEntity<?> signIn(SignInRequest request) {
        var authResponse = authenticationService.signIn(request);
        return getTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String token = getRefreshToken(request);
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(token);
        var authResponse = authenticationService.refreshToken(refreshTokenRequest);
        return getTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
    }

    public String getAccessToken(HttpServletRequest request) {
        return getCookieValueByName(request, accessCookieName);
    }

    public String getRefreshToken(HttpServletRequest request) {
        return getCookieValueByName(request, refreshCookieName);
    }

    private ResponseEntity<?> getTokens(String accessToken, String refreshToken) {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, generateAccessCookie(accessToken).toString())
                .header(HttpHeaders.SET_COOKIE, generateRefreshCookie(refreshToken).toString())
                .body("");
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
        return generateCookie(accessCookieName, accessToken, "/api/auth/v2/access-token/");
    }

    private ResponseCookie generateRefreshCookie(String refreshToken) {
        return generateCookie(refreshCookieName, refreshToken, "/api/auth/v2/refresh-token/");
    }
}
