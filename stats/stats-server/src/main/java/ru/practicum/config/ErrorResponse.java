package ru.practicum.config;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO для унифицированного ответа об ошибках.
 * Содержит код ошибки, сообщение и детали валидации.
 */
@Data
@Builder
public class ErrorResponse {
    /**
     * Код HTTP-статуса ошибки
     */
    private int errorCode;

    /**
     * Краткое сообщение об ошибке
     */
    private String message;

    /**
     * Детали ошибок валидации (если есть)
     */
    private List<String> details;
}
