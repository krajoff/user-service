package com.example.demo.controllers;

import com.example.demo.payloads.requests.SignInRequest;
import com.example.demo.payloads.requests.SignUpRequest;
import com.example.demo.payloads.response.JwtAuthenticationResponse;
import com.example.demo.services.tokens.access.AccessTokenService;
import com.example.demo.services.tokens.access.AuthenticationService;
import com.example.demo.services.tokens.refresh.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, предоставляющий API для аутентификации пользователей.
 * Этот контроллер отвечает за регистрацию новых пользователей и
 * аутентификацию существующих.
 * </p>
 */
@Tag(name = "Authentication", description = "The Authentication API")
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;

    /**
     * Конструктор для создания экземпляра контроллера аутентификации.
     *
     * @param accessTokenService    сервис для работы с аксес-токенами
     * @param refreshTokenService   сервис для работы с рефреш-токенами
     * @param authenticationService сервис для аутентификации пользователей
     */
    public AuthenticationController(AccessTokenService accessTokenService,
                                    RefreshTokenService refreshTokenService,
                                    AuthenticationService authenticationService) {
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationService = authenticationService;
    }

    /**
     * Регистрация нового пользователя.
     * <p>
     * При успешной регистрации возвращает аксес- и рефреш-токены для нового пользователя.
     * </p>
     *
     * @param request объект запроса с данными для регистрации
     * @return ответ токенами
     */
    @PostMapping("/signup")
    public JwtAuthenticationResponse register(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    /**
     * Аутентификация пользователя.
     * <p>
     * При успешной аутентификации возвращает JWT-токен для пользователя.
     * </p>
     *
     * @param request объект запроса с данными для входа
     * @return ответ с JWT-токеном
     */
    @PostMapping("/login")
    public JwtAuthenticationResponse authenticate(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
