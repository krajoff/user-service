package com.example.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(description = "Имя пользователя", example = "Николай")
    private String firstname;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String surname;

    @Schema(description = "Отчество пользователя", example = "Владимировоч")
    private String patronymic;

    @Schema(description = "Дата рождения пользователя", example = "1985-5-5")
    private LocalDate birthDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Пароль пользователя")
    private String password;

    @Schema(description = "Номер телефона пользователя", example = "+79213109999")
    private String phoneNumber;

    @Schema(description = "Логин пользователя", example = "Ivanov_NV")
    private String username;

    @Schema(description = "Электронная почта пользователя",
            example = "Ivanov_NV@mail.com")
    private String email;

    @Schema(description = "Путь к фотографии пользователя")
    private String photo;

}
