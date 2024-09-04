package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Конфигурация для генерации документации API с использованием SpringDoc OpenAPI.
 * Содержит настройки для группировки и генерации документации API.
 */
@Configuration
public class SpringDocConfig {

    /**
     * Создает конфигурацию OpenAPI для публичного API.
     *
     * @return объект GroupedOpenApi, представляющий публичный API
     */
    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/users/**")
                .build();
    }

    /**
     * Создает объект OpenAPI с настраиваемой информацией о системе.
     *
     * @param appDescription Описание системы, используемое в информации
     *                       о приложении в OpenAPI.
     * @param appVersion     Версия программы
     * @return объект OpenAPI с настраиваемой информацией
     */
    @Bean
    public OpenAPI customOpenApi(@Value("A system for managing and tracking tasks")
                                 String appDescription,
                                 @Value("0.0.1") String appVersion) {
        return new OpenAPI().info(new Info().title("Managing and tracking tasks " +
                                "application")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact().name("username")
                                .email("uzu.mail@gmail.com")))
                .servers(List.of(new Server().url("http://localhost:8080")
                                .description("Dev service"),
                        new Server().url("http://localhost:8082")
                                .description("Beta service")));
    }
}