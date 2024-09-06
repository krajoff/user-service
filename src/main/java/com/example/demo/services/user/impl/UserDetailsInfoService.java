package com.example.demo.services.user.impl;

import com.example.demo.models.user.User;
import com.example.demo.repositories.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления детальной информацией пользователя.
 */
@Service("userDetailsInfoService")
public class UserDetailsInfoService extends UserProfileService {
    public UserDetailsInfoService(UserRepository userRepository,
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

}
