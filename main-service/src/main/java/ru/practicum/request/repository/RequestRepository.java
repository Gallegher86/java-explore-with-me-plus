package ru.practicum.request.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.event.model.Event;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT COUNT(r) FROM Request r WHERE r.event.id = :eventId AND r.status = 'CONFIRMED'")
    int getConfirmedRequestCount(@Param("eventId") Long eventId);

    @Query("SELECT r FROM Request r WHERE r.event.id = :eventId ORDER BY r.created ASC")
    Page<Request> findAllByEventId(@Param("eventId") Long eventId, Pageable pageable);

    @Query("SELECT r FROM Request r " +
            "WHERE r.event.id = :eventId AND r.id IN :ids " +
            "ORDER BY r.created ASC")
    Page<Request> findRequestsByIds(@Param("eventId") Long eventId,
                                    @Param("ids") List<Long> ids,
                                    Pageable pageable);

    List<Request> findAllByEvent(@Param("event") Event event);

    Page<Request> findAllByRequester(@Param("requester") User requester, Pageable pageable);

    List<Request> findByRequester(@Param("requester") User requester);
}
