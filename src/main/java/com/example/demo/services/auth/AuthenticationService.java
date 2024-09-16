package com.example.demo.services.auth;

import com.example.demo.exceptions.auth.AuthException;
import com.example.demo.exceptions.request.WrongRequestException;
import com.example.demo.payloads.requests.SignInRequest;
import com.example.demo.payloads.requests.SignUpRequest;
import com.example.demo.models.role.Role;
import com.example.demo.models.user.User;
import com.example.demo.payloads.response.JwtAuthenticationResponse;
import com.example.demo.services.tokens.access.AccessTokenService;
import com.example.demo.services.tokens.refresh.RefreshTokenService;
import com.example.demo.services.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс, отвечающий за аутентификацию и регистрацию пользователей.
 * <p>
 * Этот сервис управляет следующими операциями:
 * - Регистрация пользователя путем создания нового пользователя и генерации
 * JWT-токена.
 * - Аутентификация пользователя путем проверки его учетных данных и генерации
 * JWT-токена.
 * </p>
 * <p>
 * Зависимости, инжектируемые в этот сервис, включают:
 * - {@link UserService} для операций, связанных с пользователями.
 * - {@link AccessTokenService} для генерации и управления токенами JWT.
 * - {@link PasswordEncoder} для кодирования паролей пользователей.
 * - {@link AuthenticationManager} для проверки подлинности учетных данных пользователей.
 * </p>
 */
@Service
public class AuthenticationService {

    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(@Qualifier("userProfileService") UserService userService,
                                 AccessTokenService accessTokenService, RefreshTokenService refreshTokenService,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Регистрация пользователя (по умолчанию у всех роль ROLE_USER)
     *
     * @param request {@link SignUpRequest}
     * @return JWT-токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        try {
            User user = User.builder()
                    .username(request.getUsername().toLowerCase())
                    .email(request.getEmail().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();

            userService.createUser(user);

            var accessToken = accessTokenService.generateToken(user);
            var refreshToken = refreshTokenService.create(user.getUsername()).getToken();
            return new JwtAuthenticationResponse(accessToken, refreshToken);

        } catch (AuthenticationException ex) {
            throw new AuthException("Ошибка аутентификации пользователя. " + ex.getMessage());
        } catch (NullPointerException ex) {
            throw new WrongRequestException("Ошибка: некорректные данные, " +
                    "проверьте поля запроса. " + ex.getMessage());
        }
    }

    /**
     * Аутентификация пользователя
     *
     * @param request {@link SignInRequest}
     * @return token
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            request.setUsername(request.getUsername().toLowerCase());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));

            var user = userService.getUserByUsername(request.getUsername());
            var accessToken = accessTokenService.generateToken(user);
            var refreshToken = refreshTokenService.recreate(user).getToken();
            return new JwtAuthenticationResponse(accessToken, refreshToken);
        } catch (AuthenticationException ex) {
            throw new AuthException("Ошибка аутентификации пользователя. " + ex.getMessage());
        } catch (NullPointerException ex) {
            throw new WrongRequestException("Ошибка: некорректные данные, " +
                    "проверьте поля запроса. " + ex.getMessage());
        }
    }


}
