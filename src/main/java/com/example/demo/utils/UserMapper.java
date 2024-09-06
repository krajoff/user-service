package com.example.demo.utils;

import com.example.demo.dtos.UserDto;
import com.example.demo.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями User, UserDto DetailedUserDto.
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param user сущность User
     * @return объект UserDto
     */
    public abstract UserDto userToUserDto(User user);

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param userDto сущность UserDto
     * @return объект User
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract User userDtoToUser(UserDto userDto);

}
