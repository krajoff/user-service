package com.example.demo.controllers;

import com.example.demo.services.tokens.refresh.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.Mapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "CRUD для рефреш-токенов", description = "API работы с рефреш-токенами")
@RequestMapping("/api/v2/refresh")
public class RefreshTokenController {

    private RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @DeleteMapping()
    public void deleteAll() {
        refreshTokenService.deleteAll();
    }

}
