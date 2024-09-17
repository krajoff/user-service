package com.example.demo.controllers;

import com.example.demo.dtos.UserDto;
import com.example.demo.services.auth.CurrentUserService;
import com.example.demo.services.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Testing access", description = "The Access test role API")
@RequestMapping("/api/v1/test")
@RestController
@AllArgsConstructor
public class TestController {

    private final CurrentUserService currentUserService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public ResponseEntity<String> testUser() {
        return getUsernameCurrentUser();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        return getUsernameCurrentUser();
    }

    private ResponseEntity<String> getUsernameCurrentUser() {
        UserDto userDto = currentUserService.getCurrentUserDto();
        return ResponseEntity.ok("Привет, " + userDto.getUsername() + "!");
    }

}
