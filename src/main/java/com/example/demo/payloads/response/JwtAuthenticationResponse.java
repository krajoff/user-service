package com.example.demo.payloads.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Объект ответа для аутентификации JWT.
 * <p>
 * Этот класс ответа, отправленный клиенту после успешной аутентификации,
 * который включает токен доступа.
 * </p>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Access and refresh tokens response")
public class JwtAuthenticationResponse {

    @Schema(description = "Token access")
    @NotNull private String accessToken;

    @Schema(description = "Type")
    private final String type = "Bearer";

    @Schema(description = "Token refresh")
    @NotNull private String refreshToken;

}
