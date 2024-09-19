# User Service Based On Access And Refresh Tokens 

User Service — приложение на основе Spring Boot,
предназначенное для управления пользователем. Для аутентификации выбраны аксес- и рефреш-токены,
обладающий стандартной структурой и простотой использования.

<details open>
<summary><b>Стек</b></summary>

1. Фреймворк: Spring boot
2. Сборщик: Gradle
3. ORM: Hibernate
4. БД: Postgres
5. Контейнер: Docker-compose
6. Документация: Springdoc-openapi, Javadoc
7. Авторизация: Spring Security (Bearer Token)

</details>

<details open> 
<summary><b>Для начала работы</b></summary>

* в терминале: docker-compose up
* swagger http://localhost:8080/swagger-ui/index.html
* добавить в переменные среды .env

</details>

<details open> 
<summary><b>Работа с пользователями</b></summary>

* Регистрация пользователя. 
Endpoint: ``POST localhost:8080/api/v1/auth/signup``

```json
{
  "username": "nikolay_vp",
  "email": "nikolay@mail.ru",
  "password": "password"
}
```

* Аутентификация только, что созданного пользователя.
Endpoint: ``POST localhost:8080/api/v1/auth/login``

```json
{
  "username": "nikolay_vp",
  "password": "password"
}
```
* Или аутентификация пользователя, который уже записан в базу данных через init.sql
```json
{
  "username": "petrov_pp",
  "password": "123456"
}
```
* Получение данных о пользователе.
Endpoint: ``GET localhost:8080/api/v1/user/petrov_pp``

```json
{
   "firstname": "Николай",
   "surname": "Яшин",
   "patronymic": "Владимирович",
   "birthDate": "2014-09-05",
   "phoneNumber": "+79001234560"
}
```

* Изменение данных пользователя.
   Endpoint: ``PUT localhost:8080/api/v1/user``

```json
{
   "firstname": "Николай",
   "surname": "Яшин",
   "patronymic": "Владимирович",
   "birthDate": "2014-09-05",
   "phoneNumber": "+79001234560"
}
```

* Изменение контактной информации пользователя.
   Endpoint: ``PUT localhost:8080/api/v1/user/contact/nikolay_vp``

```json
{
   "email": "nikolay1@mail.ru",
   "phoneNumber": "+79001234500"
}
```

* Изменение детальной информации пользователя.
Endpoint: ``PUT localhost:8080/api/v1/user/detail/nikolay_vp``

```json
{   
   "firstname": "Павел",
   "surname": "Яшин",
   "patronymic": "Владимирович",
   "email": "pavel1@mail.ru",
   "phoneNumber": "+79001234500"
}
```

* Удаление пользователя.
Endpoint: ``DELETE localhost:8080/api/v1/user/contact/nikolay_vp``


</details>
