# User Service

User Service — это приложение на основе Spring Boot,
предназначенное для управления пользователем. Для аутентификации выбран JWT,
обладающий стандартной структурой и простотой использования.

<details open>
<summary><b>Стек</b></summary>

1. Фреймворк: Spring boot
2. Сборщик: Gradle
3. ORM: Hibernate
4. БД: Postgres
5. Контейнер: Docker
6. Документация: Springdoc-openapi, Javadoc
7. Авторизация: Spring Security

</details>

<details open> 
<summary><b>Для начала работы</b></summary>

* в терминале: docker-compose up
* swagger http://localhost:8080/swagger-ui/index.html
* добавить в переменные среды .env

</details>

<details open> 
<summary><b>Работа с пользователями</b></summary>

1. Регистрация пользователя. 
Endpoint: ``POST localhost:8080/api/v1/auth/signup``

```json
{
  "username": "nikolay_vp",
  "email": "nikolay@mail.ru",
  "password": "password"
}
```

2. Аутентификация пользователя. 
Endpoint: ``POST localhost:8080/api/v1/auth/login``

```json
{
  "username": "nikolay_vp",
  "password": "password"
}
```

3. Изменение данных пользователя.
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

4. Изменение контактной информации пользователя.
   Endpoint: ``PUT localhost:8080/api/v1/user/contact/nikolay_vp``

```json
{
   "email": "nikolay1@mail.ru",
   "phoneNumber": "+79001234500"
}
```

5. Изменение детальной информации пользователя.
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

6. Удаление пользователя.
Endpoint: ``DELETE localhost:8080/api/v1/user/contact/nikolay_vp``


</details>
