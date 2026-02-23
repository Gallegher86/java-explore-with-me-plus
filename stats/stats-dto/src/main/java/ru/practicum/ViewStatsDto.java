package ru.practicum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}
