package com.example.demo.controllers;

import com.example.demo.dtos.DetailedUserDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.PermissionException;
import com.example.demo.models.user.Role;
import com.example.demo.models.user.User;
import com.example.demo.services.auth.AuthService;
import com.example.demo.services.user.UserService;
import com.example.demo.utils.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер, предоставляющий API для управления контактной
 * информацией пользователей (email и телефон). Этот контроллер
 * предоставляет методы для получения, обновления и удаления
 * информации о пользователях.
 */
@Tag(name = "Профиль пользователя (контактная информация)",
        description = "API для работы c контактной информацией пользователя")
@RequestMapping("/api/v1/admin/contact")
@RestController
public class AdminContactInformationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    /**
     * Создание пользователе.
     *
     * @return DTO с информацией о пользователе
     */
    @PostMapping
    @Operation(summary = "Создание пользователя")
    public User create(@RequestBody UserDto detailedUserDto) {
        return userService.createUser(userMapper
                .detailedUserDtoToUser(detailedUserDto));
    }

    /**
     * Получает информацию о пользователе по username.
     *
     * @return Информацией о пользователе
     */
    @GetMapping("/{username}")
    @Operation(summary = "Получение информации о пользователе")
    public User getByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Обновление информации о пользователе по username.
     *
     * @return DTO с информацией о пользователе
     */
    @PutMapping("/{username}")
    @Operation(summary = "Обновление информации о пользователе")
    public User updateByUsername(@PathVariable String username,
                                 @RequestBody DetailedUserDto detailedUserDto) {
        return userService.updateByUsername(username,
                userMapper.detailedUserDtoToUser(detailedUserDto));
    }

    /**
     * Удаляет пользователя по username.
     */
    @DeleteMapping("/{username}")
    @Operation(summary = "Удалить аккаунт")
    public void deleteByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
    }

}
