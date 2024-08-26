package net.test.repository;
import net.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Найти пользователя по логину
    Optional<User> findByLogin(String login);

    // Проверить, существует ли пользователь с таким логином
    boolean existsByLogin(String login);
}
