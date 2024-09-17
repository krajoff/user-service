package com.example.demo.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RefreshTokenRequest {

    @JsonProperty("refreshToken")
    @Schema(description = "Refresh token", example = "7175bda0-6ce0-48f4-a072-397a90ccef48")
    @NotBlank(message = "Refresh token can not be blank")
    private String refreshToken;

}