package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.request.EndPointHitDtoNew;
import ru.practicum.dto.response.EndPointHitDto;
import ru.practicum.dto.response.ViewStatsDto;
import ru.practicum.mapper.MapStructStatsMapper;
import ru.practicum.model.EndPointHit;
import ru.practicum.repository.EndPointHitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EndPointHitRepository repository;
    private final MapStructStatsMapper mapper;

    @Override
    public EndPointHitDto addHit(EndPointHitDtoNew dto) {
        log.info("Stats-service: запрос на сохранение хита: {}", dto);

        LocalDateTime timestamp = parseDate(dto.getTimestamp(), "timestamp");

        EndPointHit entity = mapper.toEntity(dto);
        entity.setTimestamp(timestamp);

        EndPointHit saved = repository.save(entity);

        EndPointHitDto result = mapper.toDto(saved);

        log.info("Stats-service: хит сохранён: {}", result);
        return result;
    }

    @Override
    public List<ViewStatsDto> getStats(String startStr,
                                       String endStr,
                                       List<String> uris,
                                       Boolean unique) {
        log.info("Stats-service: запрос статистики start={}, end={}, uris={}, unique={}",
                startStr, endStr, uris, unique);

        LocalDateTime start = parseDate(startStr, "start");
        LocalDateTime end = parseDate(endStr, "end");

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Параметр end не может быть раньше start");
        }

        // Выбираем базовую статистику из репозитория
        boolean uniqueOnly = Boolean.TRUE.equals(unique);

        List<ViewStatsDto> stats;
        if (uniqueOnly) {
            stats = repository.getUniqueStats(start, end);
        } else {
            stats = repository.getStats(start, end);
        }

        // Если передали uris — фильтруем уже в памяти
        if (uris != null && !uris.isEmpty()) {
            stats = stats.stream()
                    .filter(s -> uris.contains(s.getUri()))
                    .toList();
        }

        log.info("Stats-service: статистика получена, size={}", stats.size());
        return stats;
    }

    private LocalDateTime parseDate(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Параметр " + fieldName + " должен быть указан в формате yyyy-MM-dd HH:mm:ss");
        }
        try {
            return LocalDateTime.parse(value, FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(
                    "Параметр " + fieldName + " должен быть в формате yyyy-MM-dd HH:mm:ss: " + value);
        }
    }
}