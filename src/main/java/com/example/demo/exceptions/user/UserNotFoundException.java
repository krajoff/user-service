package com.example.demo.exceptions.user;

/**
 * Исключение, которое используется для обозначения случаев,
 * когда запрашиваемый ресурс не найден.
 * <p>
 * Это исключение расширяет {@link RuntimeException} и предназначено
 * для обработки ситуаций,
 * когда определенный ресурс, такой как пользователь, запись в базе
 * данных или другой объект,
 * не может быть найден в системе.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Создает новое исключение ResourceNotFoundException с заданным сообщением.
     *
     * @param message сообщение, которое описывает причину исключения,
     *                указывающее на то, что запрашиваемый ресурс не найден.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}