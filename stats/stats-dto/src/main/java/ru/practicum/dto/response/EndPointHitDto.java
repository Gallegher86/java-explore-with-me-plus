package ru.practicum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO для полного представления хита конечной точки (с ID).
 * Используется для ответов API с полной информацией.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EndPointHitDto {
    /**
     * Уникальный идентификатор записи
     */
    private Long id;

    /**
     * Название сервиса
     */
    private String app;

    /**
     * URI запроса
     */
    private String uri;

    /**
     * IP‑адрес клиента
     */
    private String ip;

    /**
     * Время запроса
     */
    private LocalDateTime timestamp;
}
