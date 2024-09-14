package com.example.demo.exceptions.auth;

/**
 * Исключение для обработки ошибок аутентификации.
 * Это исключение расширяет {@link RuntimeException} и предназначено для
 * обозначения ошибок, связанных с аутентификацией в приложении.
 */
public class AuthException extends RuntimeException {

    /**
     * Создает новое исключение AuthException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения.
     */
    public AuthException(String message) {
        super(message);
    }
}

