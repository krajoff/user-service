package com.example.demo.controllers;

import com.example.demo.dtos.UserDto;
import com.example.demo.models.user.User;
import com.example.demo.services.auth.AuthService;
import com.example.demo.services.user.UserService;
import com.example.demo.utils.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Профиль пользователя (контактная информация)",
        description = "API для работы c детальной информацией пользователя")
@RequestMapping("/api/v1/user/detail")
@RestController
public class DetailsController {

    @Autowired
    @Qualifier("userDetailsInfoService")
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
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userService.createUser(userMapper.userDtoToUser(userDto));
        return userMapper.userToUserDto(user);
    }

    /**
     * Получает информацию о пользователе по username.
     *
     * @return Информацией о пользователе
     */
    @GetMapping("/{username}")
    @Operation(summary = "Получение информации о пользователе")
    public UserDto getByUsername(@PathVariable String username) {
        return userMapper.userToUserDto(userService.getUserByUsername(username));
    }

    /**
     * Обновление информации о пользователе по username.
     *
     * @return DTO с информацией о пользователе
     */
    @PutMapping("/{username}")
    @Operation(summary = "Обновление информации о пользователе")
    public UserDto updateByUsername(@PathVariable String username,
                                    @RequestBody UserDto userDto) {
        User user = userService.updateByUsername(username,
                userMapper.userDtoToUser(userDto));
        return userMapper.userToUserDto(user);
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
