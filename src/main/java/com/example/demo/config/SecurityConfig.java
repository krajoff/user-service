package com.example.demo.config;

import com.example.demo.filter.JwtAuthenticationFilter;
import com.example.demo.services.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Конфигурация безопасности приложения.
 * Настраивает фильтры безопасности и правила доступа для различных URL-путей.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Конструктор для создания экземпляра SecurityConfig.
     *
     * @param userService             сервис по работе с репозиторием пользователей
     * @param jwtAuthenticationFilter фильтр для обработки JWT-аутентификации
     */
    public SecurityConfig(
            @Qualifier("userProfileService") UserService userService,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Конфигурация цепочки фильтров безопасности.
     *
     * @param http объект HttpSecurity, используемый для настройки
     *             безопасности веб-приложения
     * @return объект SecurityFilterChain, который настраивает безопасность
     * для различных URL-путей
     * @throws Exception в случае ошибок конфигурации безопасности
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v2/auth/**").permitAll()
                                .requestMatchers("/api/v2/refresh/*").permitAll()
                                .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Создает бин UserDetailsService, который предоставляет
     * информацию о пользователе на основе логина / имени пользователя.
     *
     * @return UserDetailsService, который использует метод getUserByUsername
     * из userService для получения данных пользователя.
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
     * пользователей.
     * @throws Exception в случае ошибок получения AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Создает бин AuthenticationProvider для настройки процесса аутентификации.
     *
     * @return AuthenticationProvider, который использует UserDetailsService
     * и PasswordEncoder для проверки учетных данных пользователей.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
