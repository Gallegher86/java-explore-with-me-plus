package ru.practicum.event.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JPAQueryFactory queryFactory;

    //дописать
    public Event getEventById(Long eventId) {
        return null;
    }


}
