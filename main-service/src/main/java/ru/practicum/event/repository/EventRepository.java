package ru.practicum.event.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.exception.NotFoundException;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JPAQueryFactory queryFactory;

    public Event getEventById(Long eventId) {
        Event event = queryFactory.selectFrom(event)
                .where(event.id.eq(eventId))
                .fetchOne();

        if (event == null) {
            throw new NotFoundException("Event with id=" + eventId + " was not found");
        }
        return event;
    }
}
