package com.example.demo.config;

import com.example.demo.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Конфигурация приложения в контексте SpringBoot.
 */
@Configuration
public class ApplicationConfiguration {

    private final UserService userService;

    /**
     * Конструктор сущности ApplicationConfiguration
     *
     * @param userService сервис для работы с пользователями, предоставляющий
     *                    данные пользователя по логину
     */
    public ApplicationConfiguration(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создает бин UserDetailsService, который предоставляет
     * информацию о пользователе на основе логина.
     *
     * @return UserDetailsService, который использует метод getUserByUsername
     *         из userService для получения данных пользователя.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return userService::getUserByUsername;
    }

    /**
     * Создает бин BCryptPasswordEncoder для кодирования паролей пользователей.
     *
     * @return BCryptPasswordEncoder, используемый для безопасного хранения паролей.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает бин AuthenticationManager для управления процессом аутентификации.
     *
     * @param config объект AuthenticationConfiguration, используемый для
     *               получения AuthenticationManager.
     * @return AuthenticationManager, который используется для аутентификации
     *         пользователей.
     * @throws Exception в случае ошибок получения AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Создает бин AuthenticationProvider для настройки процесса аутентификации.
     *
     * @return AuthenticationProvider, который использует UserDetailsService
     *         и PasswordEncoder для проверки учетных данных пользователей.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
