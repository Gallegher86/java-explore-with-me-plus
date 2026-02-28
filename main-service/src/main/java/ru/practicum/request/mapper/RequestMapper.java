package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.event.model.Event;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;

/**
 * Маппер для преобразования между DTO запросов участия и сущностью Request.
 */
@Mapper(componentModel = "spring")
public interface RequestMapper {

    /**
     * Преобразует сущность Request в ParticipationRequestDto.
     * @param request сущность запроса участия
     * @return DTO запроса участия с полями:
     * - created
     * - event (ID события)
     * - id
     * - requester (ID пользователя)
     * - status
     */
    @Mapping(source = "created", target = "created")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    ParticipationRequestDto toParticipationRequestDto(Request request);
//
//    /**
//     * Преобразует список сущностей Request в список DTO ParticipationRequestDto.
//     * @param requests список сущностей запросов участия
//     * @return список DTO запросов участия
//     */
//    List<ParticipationRequestDto> toParticipationRequestDtoList(List<Request> requests);
//
//    /**
//     * Преобразует User и Event в сущность Request.
//     * @param user пользователь, отправивший запрос
//     * @param event событие, на которое подаётся запрос
//     * @return сущность Request с заполненными полями:
//     * - created (текущее время)
//     * - event (ссылка на событие)
//     * - requester (ссылка на пользователя)
//     * - status (PENDING)
//     */
//    @Mapping(expression = "java(java.time.LocalDateTime.now())", target = "created")
//    @Mapping(source = "event", target = "event")
//    @Mapping(source = "user", target = "requester")
//    @Mapping(expression = "java(ru.practicum.request.model.Status.PENDING)", target = "status")
//    Request toRequest(User user, Event event);
}
