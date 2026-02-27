package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Получает пользователей с пагинацией, отсортированных по id.
     */
    Page<User> findAllByOrderByIdAsc(Pageable pageable);

    /**
     * Получает пользователей по спискам ID с пагинацией.
     */
    Page<User> findByIdInOrderByIdAsc(List<Long> ids, Pageable pageable);
}
