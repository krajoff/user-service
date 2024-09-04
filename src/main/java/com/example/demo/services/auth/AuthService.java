package com.example.demo.services.auth;

import com.example.demo.models.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс для обработки операций, связанных с аутентификацией.
 * <p>
 * Этот сервис предоставляет метод для взаимодействия с
 * текущим аутентифицированным пользователем.
 * </p>
 */
@Service
public class AuthService {

    /**
     * Получает текущего аутентифицированного пользователя.
     * <p>
     * Этот метод проверяет текущее значение {@link Authentication}
     * из {@link SecurityContextHolder}.
     * Убеждается, что аутентификация действительна и
     * principal имеет тип {@link User}.
     * Если аутентификация недействительна или principal не является
     * {@link User}, то будет выброшено исключение.
     * </p>
     *
     * @return текущий аутентифицированный {@link User}.
     */
    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException(
                    "User not authenticated or invalid authentication");
        }
        return (User) authentication.getPrincipal();
    }
}
