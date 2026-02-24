package ru.practicum.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания нового хита конечной точки.
 * Содержит валидационные ограничения для входящих данных.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EndPointHitDtoNew {

    @NotBlank(message = "Идентификатор сервиса должен быть указан.")
    @Size(max = 50, message = "Длина идентификатора сервиса не должна превышать 50 символов.")
    private String app;

    @NotBlank(message = "URI должен быть указан.")
    @Size(max = 2000, message = "Длина URI не должна превышать 2000 символов.")
    private String uri;

    @NotBlank(message = "IP-адрес пользователя должен быть указан.")
    @Size(max = 45, message = "Длина IP-адреса не должна превышать 45 символов.")
    private String ip;

    @NotBlank(message = "Время запроса должно быть указано.")
    private String timestamp;
}
