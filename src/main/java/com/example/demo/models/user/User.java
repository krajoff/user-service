package com.example.demo.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Класс пользователя в системе.
 * <p>
 * Пользователь имеет уникальный идентификатор, имя пользователя, пароль, роль,
 * email, приоритет, дату создания и обновления. Пользователь может быть
 * автором задач и иметь множество задач и комментариев.
 * Реализует интерфейс {@link UserDetails} для интеграции с Spring Security.
 * </p>
 */
@Entity(name = "User")
@Builder
@Table(name = "users")
@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Column(name = "id")
    private Long id;

    /**
     * Имя пользователя.
     */
    @Column(name = "firstname")
    @Size(max = 255)
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "surname")
    @Size(max = 255)
    private String surname;

    /**
     * Отчество пользователя.
     */
    @Column(name = "patronymic")
    @Size(max = 255)
    private String patronymic;

    /**
     * Дата рождения пользователя.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Номер телефона пользователя.
     */
    @Column(name = "phone_number")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Только + и цифры")
    private String phoneNumber;

    /**
     * Логин пользователя. Поле обязательно для заполнения и должно быть уникальным.
     */
    @Column(name = "username", unique = true, nullable = false)
    @Size(max = 255)
    private String username;

    /**
     * Пароль пользователя. Поле обязательно для заполнения.
     */
    @Column(name = "password", nullable = false)
    @Size(max = 255)
    private String password;

    /**
     * Роль пользователя в системе. Хранится в виде строки.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /**
     * Email пользователя. Должен быть уникальным и соответствовать формату email.
     */
    @Email
    @Column(name = "email", unique = true)
    @Size(max = 255)
    private String email;

    /**
     * Путь к фотографии пользователя.
     */
    @Column(name = "photo")
    @Size(max = 255)
    private String photo;

    /**
     * Дата создания пользователя. Поле автоматически заполняется
     * при создании и не может быть обновлено.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    /**
     * Дата последнего обновления пользователя. Поле автоматически
     * обновляется при изменении записи.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * Версия.
     */
    @Version
    @Builder.Default
    @Column(name = "version")
    private Long version = 1L;

    /**
     * Возвращает список ролей (прав доступа) пользователя для интеграции с Spring Security.
     *
     * @return коллекция с единственной ролью пользователя
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority =
                new SimpleGrantedAuthority(role.name());
        return List.of(grantedAuthority);
    }

    /**
     * Проверяет, истек ли срок действия учетной записи.
     *
     * @return всегда возвращает {@code true}, так как учетная запись
     * не может быть просрочена
     */
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, заблокирована ли учетная запись.
     *
     * @return всегда возвращает {@code true}, так как учетная
     * запись не может быть заблокирована
     */
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, истек ли срок действия учетных данных (пароля).
     *
     * @return всегда возвращает {@code true}, так как
     * учетные данные не могут быть просрочены
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, включена ли учетная запись пользователя.
     *
     * @return всегда возвращает {@code true}, так как учетная
     * запись всегда активна
     */
    public boolean isEnabled() {
        return true;
    }
}
