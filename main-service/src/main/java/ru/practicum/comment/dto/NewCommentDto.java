package ru.practicum.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    @NotNull(message = "id события должно быть указано.")
    @Positive(message = "id события должно быть положительным числом.")
    private Long eventId;

    @NotBlank()
    @Size(min = 3, max = 2000, message = "Комментарий должен включать от 3 до 2000 символов.")
    private String text;
}
