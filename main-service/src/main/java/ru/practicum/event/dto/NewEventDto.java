package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotBlank()
    private String annotation;

    @NotNull()
    private Integer category;

    @NotBlank()
    private String description;

    @NotNull()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull()
    private Location location;

    private boolean paid = false;

    @PositiveOrZero
    private Integer participantLimit = 0;

    private boolean requestModeration = true;

    @NotBlank()
    private String title;
}
