package com.example.demo.repositories.user;

import com.example.demo.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями пользователя.
 * <p>
 * Предоставляет методы для поиска, обновления и удаления пользователей,
 * а также проверки существования пользователей по имени.
 * Расширяет интерфейс {@link JpaRepository} для поддержки стандартных
 * CRUD-операций.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Ищет пользователя по имени пользователя (username).
     *
     * @param username имя пользователя
     * @return {@link User} с указанным именем пользователя
     */
    User findByUsername(String username) throws UsernameNotFoundException;

    /**
     * Ищет пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя
     * @return {@link Optional<User>} содержащий пользователя,
     * если он найден, или пустой, если не найден
     */
    @Query(value = "select * from users u where u.email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email) throws UsernameNotFoundException;

    /**
     * Ищет пользователей по имени пользователя или адресу электронной почты.
     *
     * @param username имя пользователя
     * @param email адрес электронной почты
     * @return список пользователей, соответствующих указанным критериям
     * @throws UsernameNotFoundException если пользователи не найдены
     */
    @Query(value = "select * from users u where u.username = ?1 or u.email = ?2", nativeQuery = true)
    List<User> findByUsernameOrEmail(String username, String email) throws UsernameNotFoundException;

    /**
     * Обновляет пароль пользователя по его имени пользователя.
     *
     * @param username имя пользователя
     * @param user объект пользователя с обновленным паролем
     * @return обновленный {@link User}
     * @throws UsernameNotFoundException если пользователь с
     * таким именем не найден
     */
    @Query(value = "update from users u set u.password = ?2.password " +
            "where u.username = ?1", nativeQuery = true)
    User updateByUsername(String username, User user)
            throws UsernameNotFoundException;

    /**
     * Проверяет существование пользователя с заданным именем пользователя.
     *
     * @param username имя пользователя
     * @return {@code true}, если пользователь с таким именем существует,
     * иначе {@code false}
     */
    boolean existsByUsername(String username);

    /**
     * Удаляет пользователя по имени пользователя (username).
     *
     * @param username имя пользователя
     */
    void deleteByUsername(String username) throws UsernameNotFoundException;
}

