package com.example.demo.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Рефреш-токен запрос")
public class RefreshTokenRequest {

    @JsonProperty("refreshToken")
    @Schema(description = "Рефреш-токен", example = "7175bda0-6ce0-48f4-a072-397a90ccef48")
    @NotBlank(message = "Рефреш-токен не может быть пустым")
    private String refreshToken;

}