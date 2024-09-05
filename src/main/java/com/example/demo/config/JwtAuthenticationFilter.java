package com.example.demo.config;

import com.example.demo.exceptions.JwtAuthException;
import com.example.demo.services.jwt.JwtService;
import com.example.demo.services.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter — это фильтр, который обрабатывает каждый входящий
 * HTTP-запрос, извлекая и проверяя JWT-токен из заголовка Authorization.
 * Если токен валиден, фильтр аутентифицирует пользователя и устанавливает
 * контекст безопасности для дальнейшей обработки.
 *
 * <p>Этот фильтр является частью цепочки фильтров Spring Security и
 * выполняется один раз за запрос.Он использует {@link JwtService}
 * для обработки токена (его извлечения и проверки) и {@link UserService}
 * для загрузки данных пользователя.</p>
 *
 * <p>Если токен отсутствует или недействителен, фильтр передаёт запрос
 * дальше по цепочке, не изменяя контекст безопасности. Если токен валиден,
 * он аутентифицирует пользователя и устанавливает аутентификацию в
 * {@link SecurityContextHolder}.</p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Префикс для JWT-токена в заголовке Authorization.
     */
    public static final String BEARER_PREFIX = "Bearer ";

    /**
     * Название HTTP-заголовка, содержащего JWT-токен.
     */
    public static final String HEADER_NAME = "Authorization";

    private final JwtService jwtService;
    private final UserService userService;

    /**
     * Фильтрует входящие запросы, проверяя наличие JWT-токена в заголовке Authorization.
     * Если токен найден и валиден, происходит извлечение данных пользователя и его аутентификация.
     *
     * @param request     HTTP-запрос
     * @param response    HTTP-ответ
     * @param filterChain цепочка фильтров для передачи запроса дальше
     * @throws ServletException если возникает ошибка при фильтрации
     * @throws IOException      если возникает ошибка ввода/вывода при фильтрации
     * @throws JwtAuthException если JWT-токен недействителен
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException, JwtAuthException {

        final String authHeader = request.getHeader(HEADER_NAME);
        String jwt = null;

        // Проверяем заголовок
        if (!StringUtils.isEmpty(authHeader) &&
                StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            jwt = authHeader.substring(BEARER_PREFIX.length());
        }

        // Проверка токена
        if (StringUtils.isEmpty(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Обрезаем, получаем имя пользователя из токена
        final String username = jwtService.extractUsername(jwt);
        if (StringUtils.isNotEmpty(username)
                && SecurityContextHolder
                .getContext().getAuthentication() == null) {

            UserDetails userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(username);

            // Если токен валиден, то аутентифицируем пользователя
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        // Если токен валиден, продолжаем выполнение фильтра
        filterChain.doFilter(request, response);
    }
}
