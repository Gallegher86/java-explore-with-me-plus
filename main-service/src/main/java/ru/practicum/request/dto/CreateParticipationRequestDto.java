package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//отдельный DTO для запроса
public class CreateParticipationRequestDto {
    private Long eventId;
}

