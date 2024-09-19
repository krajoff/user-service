package com.example.demo.controllers;

import com.example.demo.dtos.UserDto;
import com.example.demo.services.auth.CurrentUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Testing access", description = "The Access test role API")
@RequestMapping("/api/v1/test")
@RestController
public class TestController {

    @GetMapping("/anybody")
    public String allAccess() {
        return "Public content";
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user")
    public String helloUser() {
        return "User content";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String helloAdmin() {
        return "Admin content";
    }

}
