package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.Request;

/**
 * Маппер для преобразования между DTO запросов участия и сущностью Request.
 */
@Mapper(componentModel = "spring")
public interface RequestMapper {

    /**
     * Преобразует сущность Request в ParticipationRequestDto.
     */
    @Mapping(source = "created", target = "created")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    ParticipationRequestDto toParticipationRequestDto(Request request);

//    /**
//     * Преобразует список сущностей Request в список DTO ParticipationRequestDto.
//     */
//    List<ParticipationRequestDto> toParticipationRequestDtoList(List<Request> requests);
//
//    /**
//     * Преобразует User и Event в сущность Request.
//     */
//    @Mapping(target = "created", ignore = true)
//    @Mapping(source = "event", target = "event")
//    @Mapping(source = "user", target = "requester")
//    @Mapping(target = "status", ignore = true)
//    Request toRequest(User user, Event event);
//
//    @AfterMapping
//    default void setCreatedAndStatus(@MappingTarget Request request) {
//        if (request.getCreated() == null) {
//            request.setCreated(LocalDateTime.now());
//        }
//        if (request.getStatus() == null) {
//            request.setStatus(ru.practicum.request.model.Status.PENDING);
//        }
//    }
//
//    /**
//     * Валидация входных параметров перед маппингом.
//     */
//    default Request toRequestWithValidation(User user, Event event) {
//        Objects.requireNonNull(user, "User cannot be null");
//        Objects.requireNonNull(event, "Event cannot be null");
//
//        Request request = toRequest(user, event);
//        setCreatedAndStatus(request);
//        return request;
//    }
}
