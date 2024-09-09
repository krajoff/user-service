package com.example.demo.responces;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Объект ответа для аутентификации JWT.
 * <p>
 * Этот класс ответа, отправленный клиенту после успешной аутентификации,
 * который включает токен доступа.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Access token response")
public class JwtAuthenticationResponse {

    @Schema(description = "Token response")
    private String token;

}
