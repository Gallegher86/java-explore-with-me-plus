package ru.practicum.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        String message,
        String reason,
        HttpStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        List<String> errors

) {
}
