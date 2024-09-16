package com.example.demo.exceptions.request;
/**
 * Исключение для обработки ошибок не верного тела запроса.
 * Это исключение расширяет {@link RuntimeException} и предназначено для
 * обозначения ошибок, связанных с NullPointerException
 */
public class WrongRequestException extends RuntimeException {

    /**
     * Создает новое исключение WrongRequestException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения.
     */
    public WrongRequestException(String message) {
        super(message);
    }

}
