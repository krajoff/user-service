package com.example.demo.controllers.auth;

import com.example.demo.payloads.requests.SignInRequest;
import com.example.demo.payloads.requests.SignUpRequest;
import com.example.demo.services.cookies.CookieHttpOnlyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, предоставляющий API для аутентификации пользователей.
 * Этот контроллер отвечает за регистрацию новых пользователей и
 * аутентификацию существующих.
 */
@Tag(name = "Регистрация, аутентификация и обновление рефреш-токена",
        description = "API для регистрации и аутентификации с httpOnly")
@RequestMapping("/api/v2/auth")
@RestController
public class AuthCookieController {
    private final CookieHttpOnlyService cookieHttpOnlyService;

    /**
     * Конструктор для создания экземпляра контроллера аутентификации.
     *
     * @param cookieHttpOnlyService сервис для аутентификации пользователей
     */
    public AuthCookieController(CookieHttpOnlyService cookieHttpOnlyService) {
        this.cookieHttpOnlyService = cookieHttpOnlyService;
    }

    /**
     * Регистрация нового пользователя. При успешной регистрации возвращает
     * ResponseEntity с аксес- и рефреш-токеном для нового пользователя.
     *
     * @param request объект запроса с данными для регистрации
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpRequest request) {
        return cookieHttpOnlyService.signUp(request);
    }

    /**
     * Аутентификация пользователя. При успешной аутентификации
     * возвращает ResponseEntity с аксес- и рефреш-токеном
     *
     * @param request объект запроса с данными для входа
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody SignInRequest request) {
        return cookieHttpOnlyService.signIn(request);
    }

    /**
     * Обновление рефреш-токена. При успешном выполнении запроса
     * возвращает ResponseEntity с аксес- и рефреш-токеном
     *
     * @param request объект запроса с рефреш-токеном
     * @return ответ с токенами в виде кука httpOnly
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return cookieHttpOnlyService.refreshToken(request);
    }
}