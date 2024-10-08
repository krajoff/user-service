package com.example.demo.services.auth;

import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.auth.AuthException;
import com.example.demo.models.user.User;
import com.example.demo.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CurrentUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * Получает текущего аутентифицированного пользователя.
     * Этот метод проверяет текущее значение {@link Authentication}
     * из {@link SecurityContextHolder}.
     * Убеждается, что аутентификация действительна и
     * principal имеет тип {@link User}.
     * Если аутентификация недействительна или principal не является
     * {@link User}, то будет выброшено исключение.
     *
     * @return текущий аутентифицированный {@link User}.
     */
    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !(authentication.getPrincipal() instanceof User)) {
            throw new AuthException(
                    "Ошибка аутентификации пользователя");
        }
        return (User) authentication.getPrincipal();
    }

    /**
     * То же самое, но возвращает {@link UserDto}
     *
     * @return текущий аутентифицированный {@link UserDto}.
     */
    public UserDto getCurrentUserDto() {
        return userMapper.userToUserDto(getCurrentUser());
    }
}
