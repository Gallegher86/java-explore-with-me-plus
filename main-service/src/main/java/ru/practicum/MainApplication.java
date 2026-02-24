package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.practicum.dto.request.EndPointHitDtoNew;
import ru.practicum.dto.request.ViewStatsParamDto;
import ru.practicum.exceptions.StatsClientException;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        EndPointHitDtoNew hitDto = EndPointHitDtoNew.builder()
                .app("main-service")
                .uri("/utils")
                .ip("192.0.0.1")
                .timestamp(LocalDateTime.now().toString())
                .build();

        ViewStatsParamDto params = ViewStatsParamDto.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .uris(List.of("/utils"))
                .unique(true)
                .build();

        StatsClient statsClient = context.getBean(StatsClient.class);

        try {
            statsClient.hit(hitDto);
        } catch (StatsClientException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            statsClient.get(params);
        } catch (StatsClientException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
