package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Маппер для преобразования между DTO событий и сущностью Event.
 */
@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface EventMapper {

    /**
     * Преобразует NewEventDto в сущность Event.
     * Категория события загружается по ID из dto.category
     */
    @Mapping(source = "dto.annotation", target = "annotation")
    @Mapping(target = "category", ignore = true) // Требует отдельной логики загрузки по ID
    @Mapping(expression = "java(LocalDateTime.now())", target = "createdOn")
    @Mapping(source = "dto.description", target = "description")
    @Mapping(source = "dto.eventDate", target = "eventDate")
    @Mapping(target = "initiator", ignore = true) // Передаётся как отдельный параметр
    @Mapping(expression = "java(dto.getLocation().getLat())", target = "locLat")
    @Mapping(expression = "java(dto.getLocation().getLon())", target = "locLon")
    @Mapping(source = "dto.paid", target = "paid")
    @Mapping(source = "dto.participantLimit", target = "partLimit")
    @Mapping(source = "dto.requestModeration", target = "requestModeration")
    @Mapping(expression = "java(ru.practicum.event.model.State.PENDING)", target = "state")
    @Mapping(source = "dto.title", target = "title")
    Event toEvent(NewEventDto dto, Category category, User initiator);

    /**
     * Преобразует сущность Event в EventFullDto с указанием количества подтверждённых запросов и просмотров.
     */
    @Mapping(source = "annotation", target = "annotation")
    @Mapping(source = "category", target = "category", qualifiedByName = "toCategoryDto")
    @Mapping(source = "confirmedRequests", target = "confirmedRequests")
    @Mapping(source = "createdOn", target = "createdOn")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "eventDate", target = "eventDate")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "initiator", target = "initiator", qualifiedByName = "toUserShortDto")
    @Mapping(expression = "java(new ru.practicum.location.Location(event.getLocLat(), event.getLocLon()))", target = "location")
    @Mapping(source = "paid", target = "paid")
    @Mapping(source = "partLimit", target = "participantLimit")
    @Mapping(source = "publishedOn", target = "publishedOn")
    @Mapping(source = "requestModeration", target = "requestModeration")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "views", target = "views")
    EventFullDto toEventFullDto(Event event, int confirmedRequests, int views);

    /**
     * Преобразует сущность Event в EventShortDto с указанием количества подтверждённых запросов и просмотров.
     */
    @Mapping(source = "annotation", target = "annotation")
    @Mapping(source = "category", target = "category", qualifiedByName = "toCategoryDto")
    @Mapping(source = "confirmedRequests", target = "confirmedRequests")
    @Mapping(source = "eventDate", target = "eventDate")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "initiator", target = "initiator", qualifiedByName = "toUserShortDto")
    @Mapping(source = "paid", target = "paid")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "views", target = "views")
    EventShortDto toEventShortDto(Event event, int confirmedRequests, int views);

    /**
     * Преобразует список сущностей Event в список DTO EventShortDto.
     */
    List<EventShortDto> toEventShortDtoList(List<Event> events, List<Integer> confirmedRequests, List<Integer> views);
}
