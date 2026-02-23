package ru.practicum.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class ErrorResponse {
    private int errorCode;
    private String message;
    private String error;
    private List<String> details;
    private String stackTrace;
}
