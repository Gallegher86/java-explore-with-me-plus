package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class EndPointHitDtoNew {
    @NotBlank(message = "Идентификатор сервиса должен быть указан.")
    private String app;
    @NotBlank(message = "URI должен быть указан.")
    private String uri;
    @NotBlank(message = "IP-адрес пользователя должен быть указан.")
    private String ip;
    @NotBlank(message = "Время запроса должно быть указано.")
    private String timestamp;
}
