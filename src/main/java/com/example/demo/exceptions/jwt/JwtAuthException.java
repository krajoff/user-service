package com.example.demo.exceptions.jwt;

/**
 * Исключение для обработки ошибок, связанных с JWT (JSON Web Token).
 * <p>
 * Это исключение расширяет {@link RuntimeException} и предназначено для
 * обозначения ошибок, возникающих при работе с JWT, таких как
 * ошибки аутентификации или проверки токенов.
 */
public class JwtAuthException extends RuntimeException {

    /**
     * Создает новое исключение JwtAuthException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения.
     */
    public JwtAuthException(String message) {
        super(message);
    }
}
