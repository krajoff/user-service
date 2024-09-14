package com.example.demo.exceptions.auth;

public class PermissionException extends RuntimeException {

    /**
     * Создает новое исключение PermissionException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения,
     *                указывающее на то, что запрашиваемый ресурс не найден.
     */
    public PermissionException(String message) {
        super(message);
    }
}