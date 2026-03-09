package ru.practicum.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.querydsl.core.types.Predicate;
import ru.practicum.comment.model.Comment;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {

    @Query("SELECT DISTINCT c " +
            "FROM Comment c " +
            "LEFT JOIN FETCH c.author a " +
            "LEFT JOIN FETCH c.event e " +
            "WHERE c.id IN :commentIds " +
            "ORDER BY c.id")
    List<Comment> findAllByIdIn(Set<Long> commentIds);

    @EntityGraph(attributePaths = {"author", "event"})
    Page<Comment> findAll(Predicate predicate, Pageable pageable);
}