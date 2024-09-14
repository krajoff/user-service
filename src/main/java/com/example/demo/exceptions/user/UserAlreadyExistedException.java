package com.example.demo.exceptions.user;

public class UserAlreadyExistedException extends RuntimeException {

    /**
     * Создает новое исключение UserAlreadyExistedException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения,
     *                указывающее на то, что запрашиваемый ресурс не найден.
     */
    public UserAlreadyExistedException(String message) {
        super(message);
    }
}
