package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.practicum.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    @Query("SELECT COUNT(e) FROM Event e WHERE e.category.id = :catId")
    long findCountByCategory(@Param("catId") Long catId);

    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id = :userId " +
            "ORDER BY e.id ASC")
    Page<Event> findByUser(@Param("userId") Long userId, Pageable pageable);

    boolean existsByCategoryId(Long categoryId);
}
