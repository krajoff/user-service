package com.example.demo.exceptions.jwt;

public class RefreshTokenException extends RuntimeException {

    /**
     * Создает новое исключение TokenRefreshException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения,
     *                например, информацию о том, что срок действия токена истек.
     */
    public RefreshTokenException(String message) {
        super(message);
    }
}
