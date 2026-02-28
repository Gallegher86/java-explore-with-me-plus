package ru.practicum.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.practicum.error.ApiError;
import ru.practicum.exception.NotFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик исключений для main-service.
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Обрабатывает ошибки валидации DTO.
     * Возвращает 400 Bad Request с деталями полей, не прошедших валидацию.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.toList());
        logValidationError(ex, errors);

        return new ApiError(
                "Ошибки валидации данных",
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                errors
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ApiError handleWrongPath(NoResourceFoundException ex) {
        String logMessage = String.format("Получен запрос на несуществующий путь %s.", ex.getResourcePath());
        log.warn(logMessage);

        return new ApiError(
                "Ресурс по указанному пути не найден.",
                "Resource not found",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                List.of()
        );
    }

    /**
     * Обрабатывает парсинг дат и проверки start/end
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiError handleIllegalArgument(IllegalArgumentException ex) {
        String errorMessage = "Ошибка в параметрах времени запроса.";

        log.warn("Ошибка в параметрах времени: {} - {}",
                ex.getClass().getSimpleName(), ex.getMessage());

        return new ApiError(
                errorMessage,
                "Invalid argument",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                List.of(ex.getMessage())
        );
    }

    /**
     * Обрабатывает ошибку преобразования типа (например, строка вместо числа в PathVariable)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Type mismatch error for parameter '{}': {}",
                ex.getName(), ex.getMessage());

        return new ApiError(
                ex.getMessage(),
                "Incorrectly made request.",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                List.of()
        );
    }

    /**
     * Обрабатывает ошибку «Пользователь не найден»
     */
    @ExceptionHandler(NotFoundException.class)
    public ApiError handleUserNotFound(NotFoundException ex) {
        log.warn("Пользователь не найден: {}", ex.getMessage());

        return new ApiError(
                ex.getMessage(),
                "Искомый объект не был найден.",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                List.of()
        );
    }

    /**
     * Обрабатывает все остальные исключения.
     * Возвращает 500 Internal Server Error с деталями стектрейса.
     */
    @ExceptionHandler(Exception.class)
    public ApiError handleException(Exception ex) {
        String errorMessage = "Произошла ошибка на сервере.";
        log.error("Необработанное исключение: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();

        // Добавляем стектрейс в список ошибок
        List<String> errorDetails = List.of(
                ex.getMessage(),
                "Stack trace: " + stackTrace
        );

        return new ApiError(
                errorMessage,
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(),
                errorDetails
        );
    }

    private String formatFieldError(FieldError error) {
        return String.format("Поле '%s': %s", error.getField(), error.getDefaultMessage());
    }

    private void logValidationError(MethodArgumentNotValidException ex, List<String> errors) {
        log.warn("Валидация не пройдена: {} ошибок в {} полях. Детали: {}",
                errors.size(),
                ex.getBindingResult().getErrorCount(),
                errors);
    }
}
