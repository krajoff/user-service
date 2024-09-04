package com.example.demo.utils;

import com.example.demo.dtos.UserDto;
import com.example.demo.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями User и UserDto.
 * Используется для упрощения преобразования данных между слоями приложения.
 */
@Mapper(componentModel = "spring", uses = TaskMapper.class)
public abstract class UserMapper {

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param user сущность User
     * @return объект UserDto
     */
    @Mapping(source = "tasks", target = "tasks")
    public abstract UserDto userToUserDto(User user);

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param userDto сущность UserDto
     * @return объект User
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "authoredTasks", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "tasks", target = "tasks")
    public abstract User userDtoToUser(UserDto userDto);

}
