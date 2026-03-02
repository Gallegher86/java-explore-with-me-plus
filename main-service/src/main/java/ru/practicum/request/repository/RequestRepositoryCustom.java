package ru.practicum.request.repository;

import ru.practicum.event.model.Event;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.Status;
import ru.practicum.user.model.User;

import java.util.List;

public interface RequestRepositoryCustom {
    int confirmedCount(Long eventId);

    List<Request> findAllByEventId(Long eventId);

    List<Request> findRequestsByIds(Long eventId, List<Long> ids);

    List<Request> findAllByEvent(Event event);

    List<Request> findAllByRequester(User requester);

    boolean existsByRequesterAndEvent(User user, Event event);

    List<Request> findRequestsByStatusAndEvent(Status status, Long eventId);
}