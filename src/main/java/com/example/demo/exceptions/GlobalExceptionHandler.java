package com.example.demo.exceptions;

import com.example.demo.exceptions.auth.AuthException;
import com.example.demo.exceptions.request.WrongRequestException;
import com.example.demo.exceptions.auth.PermissionException;
import com.example.demo.exceptions.jwt.JwtAuthException;
import com.example.demo.exceptions.jwt.JwtExpiredException;
import com.example.demo.exceptions.user.UserAlreadyExistedException;
import com.example.demo.exceptions.user.UserNotFoundException;
import com.example.demo.models.errors.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler — это глобальный обработчик исключений, который
 * перехватывает различные исключения в приложении и возвращает соответствующие
 * HTTP-ответы с информацией об ошибке и статусами.
 *
 * <p>Этот класс использует аннотацию {@link RestControllerAdvice} для глобального
 * перехвата исключений, выбрасываемых в контроллерах. Каждое исключение
 * обрабатывается соответствующим методом, который возвращает объект
 * {@link ResponseEntity}, содержащий информацию об ошибке в виде
 * {@link AppError} и HTTP-статус.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link UserNotFoundException}, которое
     * возникает, когда запрашиваемый пользователь не найден.
     *
     * @param e исключение {@link UserNotFoundException}
     * @return ответ с кодом 404 NOT FOUND и сообщением об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(),
                        e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение {@link AuthException}, которое
     * возникает при ошибке аутентификации.
     *
     * @param e исключение {@link AuthException}
     * @return ответ с кодом 401 UNAUTHORIZED и сообщением об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchAuthException (AuthException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.UNAUTHORIZED.value(),
                        e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Обрабатывает исключение {@link JwtAuthException}, которое возникает
     * при ошибке проверки JWT-токена.
     *
     * @param e исключение {@link JwtAuthException}
     * @return ответ с кодом 401 UNAUTHORIZED и сообщением об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchJwtAuthException (JwtAuthException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.UNAUTHORIZED.value(),
                        e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Обрабатывает исключение {@link JwtExpiredException}, которое возникает,
     * когда срок действия JWT-токена истек.
     *
     * @param e исключение {@link JwtExpiredException}
     * @return ответ с кодом 401 UNAUTHORIZED и сообщением об истечении срока
     * действия токена
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchJwtExpiredException (JwtExpiredException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.UNAUTHORIZED.value(),
                        e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Обрабатывает исключение {@link UsernameNotFoundException},
     * которое возникает, если пользователь не найден.
     *
     * @param e исключение {@link UsernameNotFoundException}
     * @return ответ с кодом 404 NOT FOUND и сообщением об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchUserNotFoundException (UsernameNotFoundException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(),
                        e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение {@link UserAlreadyExistedException},
     * которое возникает, если пользователь уже существует.
     *
     * @param e исключение {@link UserAlreadyExistedException}
     * @return ответ с кодом BAD_REQUEST и сообщением об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<AppError> UserAlreadyExistedException (UserAlreadyExistedException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение {@link PermissionException},
     * которое возникает, если доступ закрыт.
     *
     * @param e исключение {@link PermissionException}
     * @return ответ с кодом FORBIDDEN и сообщением об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<AppError> PermissionException (PermissionException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
    }

    /**
     * Обрабатывает исключение {@link WrongRequestException},
     * которое возникает, если доступ закрыт.
     *
     * @param e исключение {@link WrongRequestException}
     * @return ответ с кодом FORBIDDEN и сообщением об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<AppError> WrongRequestException (WrongRequestException e) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.FORBIDDEN.value(),
                        e.getMessage()), HttpStatus.FORBIDDEN);
    }

}
