package com.example.demo.exceptions;

public class PermissionException extends RuntimeException {

    /**
     * Создает новое исключение PermitionException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения,
     *                указывающее на то, что запрашиваемый ресурс не найден.
     */
    public PermissionException(String message) {
        super(message);
    }
}