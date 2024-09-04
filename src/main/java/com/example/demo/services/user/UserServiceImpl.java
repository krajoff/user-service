package com.example.demo.services.user;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.user.Role;
import com.example.demo.models.user.User;
import com.example.demo.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления пользователями.
 * Этот сервис предоставляет методы для выполнения
 * CRUD-операций над сущностями пользователей.
 */
@Service
public class UserServiceImpl implements
        UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Получает пользователя по его уникальному идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     * @return пользователь с указанным идентификатором.
     * @throws ResourceNotFoundException если пользователь с указанным ID не существует.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User with id " + id + " not found"));
    }

    /**
     * Получает пользователя по его имени пользователя.
     *
     * @param username имя пользователя.
     * @return пользователь с указанным именем,
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Получает пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя.
     * @return пользователь с указанным адресом электронной почты.
      */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User with email " + email + " not found"));
    }

    /**
     * Создает нового пользователя.
     *
     * @param user сущность, которая должна быть создана.
     * @return пользователь.
     */
    public User createUser(User user) {
        if (!userRepository.findByUsernameOrEmail
                (user.getUsername(), user.getEmail()).isEmpty()) {
            throw new RuntimeException("User already existed");
        }
        return saveUser(user);
    }

    /**
     * Обновляет существующего пользователя по ID.
     *
     * @param id   уникальный идентификатор пользователя.
     * @param user сущность пользователя с обновленной информацией.
     * @return обновленный пользователь.
     */
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return existingUser;
    }

    /**
     * Обновляет существующего пользователя по имени.
     *
     * @param username имя пользователя для обновления.
     * @param user     сущность пользователя с информацией об обновлении.
     * @return обновленный пользователь.
     */
    public User updateByUsername(String username, User user) {
        User existingUser = getUserByUsername(username);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return existingUser;
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя для удаления.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Удаляет пользователя по имени пользователя.
     *
     * @param username имя пользователя, которого нужно удалить.
     */
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    /**
     * Сохраните сущности в БД
     *
     * @param user пользователь для сохранения
     * @return сохраненный пользователь.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Предоставляет сервис UserServiceDetailsService,
     * который загружает данные о пользователях по имени пользователя.
     *
     * @return экземпляр UserDetailsService.
     */
    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    /**
     * Получает текущего аутентифицированного пользователя.
     *
     * @return текущий аутентифицированный пользователь.
     */
    public User getCurrentUser() {
        var username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return getUserByUsername(username);
    }

    /**
     * Загружает данные пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя.
     * @return UserDetails пользователя.
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User with email " + email + " not found"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .role(Role.USER)
                .build();
    }
}
