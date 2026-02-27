//package ru.practicum.compilation.mapper;
//
//import org.mapstruct.*;
//import ru.practicum.compilation.dto.CompilationDto;
//import ru.practicum.compilation.dto.NewCompilationDto;
//import ru.practicum.compilation.dto.UpdateCompilationRequest;
//import ru.practicum.compilation.model.Compilation;
//import ru.practicum.event.dto.EventShortDto;
//import ru.practicum.event.mapper.EventMapper;
//import ru.practicum.event.model.Event;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * Маппер для преобразования между DTO подборок событий и сущностью Compilation.
// */
//@Mapper(
//        componentModel = "spring",
//        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//        uses = {EventMapper.class}
//)
//public interface CompilationMapper {
//
//    /**
//     * Преобразует NewCompilationDto в сущность Compilation.
//     * @param dto DTO с данными для создания подборки
//     * @return сущность Compilation с заполненными полями (без событий — требует отдельной логики)
//     */
//    @Mappings({
//            @Mapping(target = "events", ignore = true),
//            @Mapping(source = "pinned", target = "pinned"),
//            @Mapping(source = "title", target = "title")
//    })
//    Compilation toCompilation(NewCompilationDto dto);
//
//    /**
//     * Преобразует UpdateCompilationRequest в сущность Compilation (частичное обновление).
//     * @param request DTO с данными для обновления подборки
//     * @param compilation существующая сущность Compilation
//     */
//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "events", ignore = true),
//            @Mapping(source = "pinned", target = "pinned"),
//            @Mapping(source = "title", target = "title")
//    })
//    void updateCompilationFromDto(UpdateCompilationRequest request, @MappingTarget Compilation compilation);
//
//    /**
//     * Преобразует сущность Compilation в CompilationDto.
//     * @param compilation сущность подборки событий
//     * @return DTO подборки с полями id, pinned, title и списком событий (EventShortDto)
//     */
//    @Mappings({
//            @Mapping(source = "events", target = "events", qualifiedByName = "toEventShortDtoList")
//    })
//    CompilationDto toCompilationDto(Compilation compilation);
//
//    /**
//     * Вспомогательный метод для преобразования Set<Event> в List<EventShortDto>.
//     * Используется внутри toCompilationDto.
//     */
//    @Named("toEventShortDtoList")
//    List<EventShortDto> toEventShortDtoList(Set<Event> events);
//}
