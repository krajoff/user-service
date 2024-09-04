package com.example.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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

    @Schema(description = "Имя пользователя", example = "Nikolay")
    private String username;

    @Schema(description = "Электронная почта пользователя",
            example = "Nikolay@mail.com")
    private String email;

}
