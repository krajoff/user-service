FROM postgres:13.2-alpine

# Переменные окружения для настроек PostgreSQL
ENV POSTGRES_DB userservice
ENV POSTGRES_USER root
ENV POSTGRES_PASSWORD password

# Копируем файл init.sql в папку /docker-entrypoint-initdb.d/
ARG INIT_FILE= init.sql
COPY ${INIT_FILE} /docker-entrypoint-initdb.d/