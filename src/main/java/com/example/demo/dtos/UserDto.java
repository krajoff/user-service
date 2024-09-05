package com.example.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;

import java.util.Date;

/**
 * DTO (Data Transfer Object) для передачи данных о пользователе.
 * Содержит основную информацию о пользователе, используемую для
 * обмена данными между слоями приложения.
 */
@Data
@Schema(description = "DTO для пользователя")
public class UserDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Николай")
    private String firstname;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String surname;

    @Schema(description = "Отчество пользователя", example = "Владимировоч")
    private String patronymic;

    @Schema(description = "Дата рождения пользователя", example = "5.5.1985")
    private LocalDate birthDate;

    @Schema(description = "Логин пользователя", example = "Ivanov_NV")
    private String username;

    @Schema(description = "Электронная почта пользователя",
            example = "Ivanov_NV@mail.com")
    private String email;

    @Schema(description = "Путь к фотографии пользователя")
    private String photo;

    @Schema(description = "Дата создания пользователя")
    private Date createdAt;

    @Schema(description = "Дата последнего обновления пользователя")
    private Date updatedAt;

}
