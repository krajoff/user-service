package com.example.demo.services.jwt;

import com.example.demo.exceptions.AuthException;
import com.example.demo.requests.SignInRequest;
import com.example.demo.requests.SignUpRequest;
import com.example.demo.models.role.Role;
import com.example.demo.models.user.User;
import com.example.demo.responces.JwtAuthenticationResponse;
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
 * - {@link JwtService} для генерации и управления токенами JWT.
 * - {@link PasswordEncoder} для кодирования паролей пользователей.
 * - {@link AuthenticationManager} для проверки подлинности учетных данных
 * пользователей.
 * </p>
 */
@Service
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(@Qualifier("userProfileService")
                                 UserService userService,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Регистрация пользователя (по умолчанию у всех роль ROLE_USER
     *
     * @param request {@link SignUpRequest}
     * @return JWT-токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername().toLowerCase())
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.createUser(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request {@link SignInRequest}
     * @return token
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        request.setUsername(request.getUsername().toLowerCase());
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));

            var user = userService.getUserByUsername(request.getUsername());
            var jwt = jwtService.generateToken(user);
            return new JwtAuthenticationResponse(jwt);

        } catch (AuthenticationException ex) {
            throw new AuthException("Ошибка аутентификации пользователя: "
                    + ex.getMessage());
        }
    }



}
