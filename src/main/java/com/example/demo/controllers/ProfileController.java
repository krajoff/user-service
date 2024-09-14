package com.example.demo.controllers;

import com.example.demo.dtos.UserDto;
import com.example.demo.models.user.User;
import com.example.demo.services.auth.CurrentUserService;
import com.example.demo.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер, предоставляющий API для управления информацией пользователя.
 * Этот контроллер предоставляет методы для получения, обновления и удаления
 * информации о текущем пользователе.
 */
@Tag(name = "Профиль пользователя",
        description = "API для работы любого пользователя со своими данными")
@RequestMapping("/api/v1/user")
@RestController
public class ProfileController {

    private final UserService userService;

    private final CurrentUserService currentUserService;

    public ProfileController(@Qualifier("userProfileService") UserService userService,
                             CurrentUserService currentUserService) {
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    /**
     * Получает информацию о текущем пользователе.
     *
     * @return DTO с информацией о текущем пользователе
     */
    @GetMapping()
    @Operation(summary = "Получение информации пользователя о самом себе")
    public UserDto getCurrentUser() {
        return currentUserService.getCurrentUserDto();
    }

    /**
     * Обновляет информацию о текущем пользователе.
     *
     * @param userDto DTO с обновленной информацией о пользователе
     * @return обновленная информация о пользователе в виде DTO
     */
    @PutMapping()
    @Operation(summary = "Обновление информации пользователя о самом себе")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        User user = currentUserService.getCurrentUser();
        return userService.updateByUsername(user.getUsername(), userDto);
    }

    /**
     * Удаляет текущего пользователя.
     *
     * @return HTTP-ответ с кодом состояния OK при успешном удалении
     * или UserNotFoundException при возникновении ошибки
     */
    @DeleteMapping()
    @Operation(summary = "Удалить аккаунт")
    public ResponseEntity<?> deleteUser() {
        User user = currentUserService.getCurrentUser();
        userService.deleteUserByUsername(user.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
