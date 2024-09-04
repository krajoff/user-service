package com.example.demo.exceptions;

/**
 * Исключение для обработки случаев истечения срока действия JWT (JSON Web Token).
 * <p>
 * Это исключение расширяет {@link RuntimeException} и предназначено для
 * обозначения ситуаций, когда JWT токен просрочен и больше не действителен.
 */
public class JwtExpiredException extends RuntimeException {

    /**
     * Создает новое исключение JwtExpiredException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения,
     *                например, информацию о том, что срок действия токена истек.
     */
    public JwtExpiredException(String message) {
        super(message);
    }
}
