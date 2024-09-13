package com.example.demo.models.token;

import com.example.demo.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "RefreshToken")
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class RefreshToken {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private long id;

    /**
     * Пользователь связанный с токеном.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Токен
     */
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    /**
     * Время действия токена.
     */
    @Column(name = "expiration", nullable = false)
    private Date expiration;

}
