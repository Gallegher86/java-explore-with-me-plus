package ru.practicum.event.mapper;

import org.mapstruct.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface EventMapper {

//    @Mapping(source = "annotation", target = "annotation")
//    @Mapping(target = "category", ignore = true)
//    @Mapping(expression = "java(LocalDateTime.now())", target = "createdOn")
//    @Mapping(source = "description", target = "description")
//    @Mapping(source = "eventDate", target = "eventDate")
//    @Mapping(target = "initiator", ignore = true)
//    @Mapping(target = "locLat", expression = "java(dto.getLocation().getLat())")
//    @Mapping(target = "locLon", expression = "java(dto.getLocation().getLon())")
//    @Mapping(source = "paid", target = "paid")
//    @Mapping(source = "participantLimit", target = "partLimit")
//    @Mapping(source = "requestModeration", target = "requestModeration")
//    @Mapping(expression = "java(ru.practicum.event.model.State.PENDING)", target = "state")
//    @Mapping(source = "title", target = "title")
//    Event toEvent(NewEventDto dto, Category category, User initiator);
//
//
//    @AfterMapping
//    default void setCategoryAndInitiator(NewEventDto dto, @MappingTarget Event event, Category category, User initiator) {
//        event.setCategory(category);
//        event.setInitiator(initiator);
//    }
//
//    @Mapping(source = "annotation", target = "annotation")
//    @Mapping(source = "category", target = "category", qualifiedByName = "toCategoryDto")
//    @Mapping(source = "confirmedRequests", target = "confirmedRequests")
//    @Mapping(source = "createdOn", target = "createdOn")
//    @Mapping(source = "description", target = "description")
//    @Mapping(source = "eventDate", target = "eventDate")
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "initiator", target = "initiator", qualifiedByName = "toUserShortDto")
//    @Mapping(target = "location", expression = "java(new Location(event.getLocLat(), event.getLocLon()))")
//    @Mapping(source = "paid", target = "paid")
//    @Mapping(source = "partLimit", target = "participantLimit")
//    @Mapping(source = "publishedOn", target = "publishedOn")
//    @Mapping(source = "requestModeration", target = "requestModeration")
//    @Mapping(source = "state", target = "state")
//    @Mapping(source = "title", target = "title")
//    @Mapping(source = "views", target = "views")
//    EventFullDto toEventFullDto(Event event, int confirmedRequests, int views);
//
//    @Mapping(source = "annotation", target = "annotation")
//    @Mapping(source = "category", target = "category", qualifiedByName = "toCategoryDto")
//    @Mapping(source = "confirmedRequests", target = "confirmedRequests")
//    @Mapping(source = "eventDate", target = "eventDate")
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "initiator", target = "initiator", qualifiedByName = "toUserShortDto")
//    @Mapping(source = "paid", target = "paid")
//    @Mapping(source = "title", target = "title")
//    @Mapping(source = "views", target = "views")
//    EventShortDto toEventShortDto(Event event, int confirmedRequests, int views);
//
//    @Named("toCategoryDto")
//    CategoryDto toCategoryDto(Category category);
//
//    @Named("toUserShortDto")
//    UserShortDto toUserShortDto(User user);

//    default List<EventShortDto> toEventShortDtoList(List<Event> events, List<Integer> confirmedRequests, List<Integer> views) {
//        if (events == null || confirmedRequests == null || views == null) {
//            return null;
//        }
//        if (events.size() != confirmedRequests.size() || events.size() != views.size()) {
//            throw new IllegalArgumentException("Lists must have the same size");
//        }
//        return IntStream.range(0, events.size())
//                .mapToObj(i -> toEventShortDto(events.get(i), confirmedRequests.get(i), views.get(i)))
//                .collect(Collectors.toList());
//    }
}
