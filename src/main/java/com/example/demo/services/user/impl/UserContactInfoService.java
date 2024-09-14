package com.example.demo.services.user.impl;

import com.example.demo.models.user.User;
import com.example.demo.repositories.user.UserRepository;
import com.example.demo.utils.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления контактами пользователя.
 */
@Service("userContactInfoService")
public class UserContactInfoService extends UserProfileService {
    public UserContactInfoService(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  UserMapper userMapper) {
        super(userRepository, passwordEncoder, userMapper);
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User updateByUsername(String username, User user) {
        User existingUser = getUserByUsername(username);
        updateProfile(existingUser, user);
        saveUser(existingUser);
        return existingUser;
    }
    protected User updateProfile(User existingUser, User user) {
        if (user.getPhoneNumber() != null)
            existingUser.setPhoneNumber(user.getPhoneNumber());
        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());
        return existingUser;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
