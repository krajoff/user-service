package com.example.demo.controllers;

import com.example.demo.payloads.requests.RefreshTokenRequest;
import com.example.demo.payloads.requests.SignInRequest;
import com.example.demo.payloads.requests.SignUpRequest;
import com.example.demo.services.cookies.CookieHttpOnlyService;
import com.example.demo.services.tokens.refresh.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, предоставляющий API для управления информацией пользователя.
 * Этот контроллер предоставляет методы для получения, обновления и удаления
 * информации о текущем пользователе.
 */
@Tag(name = "Регистрация и аутентификация",
        description = "API для регистрации и аутентификации httpOnly")
@RequestMapping("/api/v2/auth")
@RestController
public class CurrentUserController {
    private final CookieHttpOnlyService cookieHttpOnlyService;
    private final RefreshTokenService refreshTokenService;

    /**
     * Конструктор для создания экземпляра контроллера аутентификации.
     *
     * @param cookieHttpOnlyService сервис для аутентификации пользователей
     */
    public CurrentUserController(CookieHttpOnlyService cookieHttpOnlyService, RefreshTokenService refreshTokenService) {
        this.cookieHttpOnlyService = cookieHttpOnlyService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Регистрация нового пользователя.
     * <p>
     * При успешной регистрации возвращает ResponseEntity с
     * аксес- и рефреш-токеном для нового пользователя.
     * </p>
     *
     * @param request объект запроса с данными для регистрации
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpRequest request) {
        return cookieHttpOnlyService.signUp(request);
    }

    /**
     * Аутентификация пользователя.
     * <p>
     * При успешной аутентификации возвращает ResponseEntity
     * с аксес- и рефреш-токеном
     * </p>
     *
     * @param request объект запроса с данными для входа
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody SignInRequest request) {
        return cookieHttpOnlyService.signIn(request);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        return cookieHttpOnlyService.refreshToken(request);
    }
}