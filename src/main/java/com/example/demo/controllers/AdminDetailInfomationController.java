package com.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Профиль пользователя (контактная информация)",
        description = "API для работы c детальной информацией пользователя")
@RequestMapping("/api/v1/admin/full")
@RestController
public class AdminDetailInfomationController {
}
