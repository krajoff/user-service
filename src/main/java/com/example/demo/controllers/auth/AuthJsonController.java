package com.example.demo.controllers.auth;

import com.example.demo.payloads.requests.RefreshTokenRequest;
import com.example.demo.payloads.requests.SignInRequest;
import com.example.demo.payloads.requests.SignUpRequest;
import com.example.demo.payloads.response.JwtAuthenticationResponse;
import com.example.demo.services.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, предоставляющий API для аутентификации пользователей.
 * Этот контроллер отвечает за регистрацию новых пользователей и
 * аутентификацию существующих.
 */
@Tag(name = "Регистрация, аутентификация и обновление рефреш-токена",
        description = "API для регистрации и аутентификации")
@RequestMapping("/api/v1/auth")
@RestController
public class AuthJsonController {
    private final AuthenticationService authenticationService;

    /**
     * Конструктор для создания экземпляра контроллера аутентификации.
     *
     * @param authenticationService сервис для аутентификации пользователей
     */
    public AuthJsonController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Регистрация нового пользователя. При успешной регистрации возвращает
     * ResponseEntity с аксес- и рефреш-токеном для нового пользователя.
     *
     * @param request объект запроса с данными для регистрации
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/signup")
    public JwtAuthenticationResponse register(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    /**
     * Аутентификация пользователя. При успешной аутентификации
     * возвращает ResponseEntity с аксес- и рефреш-токеном
     *
     * @param request объект запроса с данными для входа
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/login")
    public JwtAuthenticationResponse authenticate(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }

    /**
     * Обновление рефреш-токена. При успешном выполнении запроса
     * возвращает ResponseEntity с аксес- и рефреш-токеном
     *
     * @param request объект запроса с рефреш-токеном
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/refresh-token")
    public JwtAuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authenticationService.refreshToken(request);
    }
}
