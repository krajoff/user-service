package com.example.demo.services.user.impl;

import com.example.demo.models.user.User;
import com.example.demo.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления контактами пользователя.
 */
@Service("userContactInfoService")
public class UserContactInfoService extends UserProfileService {
    public UserContactInfoService(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        super(userRepository, passwordEncoder);
    }

    @Override
    protected User updateProfile(User existingUser, User user) {
        if (user.getPhoneNumber() != null)
            existingUser.setPhoneNumber(user.getPhoneNumber());
        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());
        return existingUser;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
