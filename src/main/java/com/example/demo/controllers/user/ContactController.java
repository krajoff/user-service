package com.example.demo.controllers.user;

import com.example.demo.dtos.UserDto;
import com.example.demo.models.user.User;
import com.example.demo.services.user.UserService;
import com.example.demo.utils.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, предоставляющий API для управления контактной
 * информацией пользователей (email и телефон). Этот контроллер
 * предоставляет методы для получения, обновления и удаления
 * информации о пользователях.
 */

@Tag(name = "Профиль пользователя (контактная информация)",
        description = "API для работы c контактной информацией пользователя")
@RequestMapping("/api/v1/user/contact")
@RestController
public class ContactController {

    private UserService userService;

    private UserMapper userMapper;


    public ContactController(@Qualifier("userContactInfoService") UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;

    }

    /**
     * Создание пользователя.
     *
     * @return DTO с информацией о пользователе
     */
    @PostMapping()
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
        return userMapper.userToUserDto(userService
                .getUserByUsername(username));
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
        System.out.println(userDto);
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
