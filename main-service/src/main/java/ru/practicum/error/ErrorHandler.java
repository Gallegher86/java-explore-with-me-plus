package ru.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.CategoryAlreadyExistException;
import ru.practicum.exception.CategoryNotEmptyException;
import ru.practicum.exception.NotFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    /**
     * Обрабатывает ошибки валидации DTO.
     * Возвращает 400 Bad Request с деталями полей, не прошедших валидацию.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.toList());
        logValidationError(ex, errors);
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .reason("Ошибки валидации данных")
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Обрабатывает NotFoundException.
     * Возвращает 404 Not Found c деталями ошибки.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException ex) {
        log.warn("Ресурс не найден: {}", ex.getMessage());
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .reason("Ресурс не найден.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Обрабатывает CategoryAlreadyExistException в category.
     * Возвращает 409 Conflict c деталями ошибки.
     */
    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleNotFoundException(CategoryAlreadyExistException ex) {
        log.warn("Категория уже существует: {}", ex.getMessage());
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .reason("Категория уже существует.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Обрабатывает CategoryNotEmptyException в category.
     * Возвращает 409 Conflict c деталями ошибки.
     */
    @ExceptionHandler(CategoryNotEmptyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleNotFoundException(CategoryNotEmptyException ex) {
        log.warn("Удаление Категории невозможно, в Категории еще есть события: {}", ex.getMessage());
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .reason("Категория не может быть удалена.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Обрабатывает все остальные исключения.
     * Возвращает 500 Internal Server Error с деталями стека вызовов.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(final Exception ex) {
        String errorMessage = "Произошла ошибка на сервере.";
        log.error("Необработанное исключение: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        return ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Произошла ошибка на сервере.")
                .reason(ex.getMessage())
                .errors(List.of(stackTrace))
                .timestamp(LocalDateTime.now())
                .build();
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
