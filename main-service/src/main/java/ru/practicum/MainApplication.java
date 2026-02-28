package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.practicum.dto.request.EndPointHitDtoNew;
import ru.practicum.dto.request.ViewStatsParamDto;
import ru.practicum.dto.response.ViewStatsDto;
import ru.practicum.exceptions.StatsClientException;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        LocalDateTime now = LocalDateTime.now();

        EndPointHitDtoNew hitDto = EndPointHitDtoNew.builder()
                .app("main-service")
                .uri("/test")
                .ip("192.0.0.1")
                .timestamp(now)
                .build();

        ViewStatsParamDto params = ViewStatsParamDto.builder()
                .start(now.minusDays(1))
                .end(now.plusDays(1))
                .uris(List.of("/test"))
                .unique(true)
                .build();

        StatsClient statsClient = context.getBean(StatsClient.class);

        try {
            statsClient.hit(hitDto);
        } catch (StatsClientException ex) {
            System.out.println(ex.getMessage());
        }

        List<ViewStatsDto> stats = null;

        try {
            stats = statsClient.get(params);
        } catch (StatsClientException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(stats);
    }
}
