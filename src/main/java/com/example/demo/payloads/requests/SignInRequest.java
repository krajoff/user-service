package com.example.demo.payloads.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO для запросов на аутентификацию пользователя.
 * <p>
 * Этот класс инкапсулирует данные, необходимые пользователю для авторизации.
 * Он включает ограничения проверки, чтобы убедиться, что предоставленные
 * имя пользователя и пароль соответствуют требуемому формату и длине.
 * </p>
 */
@Setter
@Getter
@Schema(description = "Sign in request")
public class SignInRequest {

    @Schema(description = "username", example = "Ivanov_LS")
    @Size(min = 5, max = 30,
            message = "Minimum username length is 5 letters, maximum is 30")
    @NotBlank(message = "Username can not be blank")
    private String username;

    @Schema(description = "password")
    @Size(max = 255, message = "Password maximum length is 255 letters")
    private String password;
}

