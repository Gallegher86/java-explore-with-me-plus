package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import ru.practicum.exceptions.StatsClientException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class StatsClient {
    private final RestClient restClient;

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String statsServerUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(statsServerUrl)
                .defaultStatusHandler(
                        HttpStatusCode::isError,
                        (request, response) -> {
                            String body = new String(
                                    response.getBody().readAllBytes(),
                                    StandardCharsets.UTF_8
                            );

                            log.warn(
                                    "Ошибка вызова Stats-client, status: {}, body: {}",
                                    response.getStatusCode(),
                                    body
                            );

                            throw new StatsClientException(response.getStatusCode(), body);
                        }
                )
                .build();
    }

    public EndPointHitDto hit(EndPointHitDtoNew hitDto) {
        log.info("Stats-client получен запрос на отправку данных - hitDto: {}", hitDto);
        EndPointHitDto response = restClient.post()
                .uri(uriBuilder -> uriBuilder.path("/hit").build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(hitDto)
                .retrieve()
                .body(EndPointHitDto.class);
        log.info("Stats-server ответил на запрос Stats-client, данные записаны: {}", response);
        return response;
    }

    public List<ViewStatsDto> get(ViewStatsParamDto paramDto) {
        log.info("Stats-client получен запрос на получение статистики с параметрами: {}", paramDto);
        List<ViewStatsDto> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", paramDto.getStart())
                        .queryParam("end", paramDto.getEnd())
                        .queryParamIfPresent("uris",
                                Optional.ofNullable(
                                        CollectionUtils.isEmpty(paramDto.getUris())
                                                ? null
                                                : paramDto.getUris()))
                        .queryParam("unique", paramDto.getUnique())
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        log.info("Stats-server ответил на запрос статистики от Stats-client.");
        return response;
    }
}
