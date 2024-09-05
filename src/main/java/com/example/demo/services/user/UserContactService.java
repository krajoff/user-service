package com.example.demo.services.user;

import com.example.demo.dtos.DetailedUserDto;

public interface UserContactService {

    DetailedUserDto saveUser(DetailedUserDto detailedUserDto);
    DetailedUserDto getUserByUsername(String username);
    DetailedUserDto updateUserByUsername(String username,
                                      DetailedUserDto detailedUserDto);
    void deleteUserByUsername(String username);
}
