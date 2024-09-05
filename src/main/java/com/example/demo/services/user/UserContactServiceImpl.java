package com.example.demo.services.user;

import com.example.demo.dtos.DetailedUserDto;
import com.example.demo.models.user.User;
import com.example.demo.repositories.user.UserRepository;
import com.example.demo.utils.UserMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления пользователями.
 * Этот сервис предоставляет методы для выполнения
 * CRUD-операций над сущностями пользователей.
 */
@Service
public class UserContactServiceImpl implements
        UserContactService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserContactServiceImpl(UserRepository userRepository,
                                  @Lazy PasswordEncoder passwordEncoder,
                                  UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public DetailedUserDto saveUser(DetailedUserDto dto) {
        return userMapper.userToDetailedUserDto(
                userRepository.save(userMapper.detailedUserDtoToUser(dto)));
    }

    public DetailedUserDto getUserByUsername(String username) {
        return userMapper.userToDetailedUserDto(
                userRepository.findByUsername(username));
    }

    public DetailedUserDto updateUserByUsername(String username,
                                                DetailedUserDto dto) {
        DetailedUserDto existingUser = getUserByUsername(username);
        existingUser.setFirstname(dto.getFirstname());
        existingUser.setSurname(dto.getSurname());
        existingUser.setPatronymic(dto.getPatronymic());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setEmail(dto.getEmail());
        return saveUser(existingUser);

    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
