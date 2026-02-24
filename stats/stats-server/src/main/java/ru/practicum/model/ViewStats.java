package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность для хранения агрегированных статистических данных по хитам.
 * Представляет собой материализованное представление агрегированной статистики.
 */
@Entity
@Table(name = "view_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app", nullable = false, length = 255)
    private String app;

    @Column(name = "uri", nullable = false, length = 1000)
    private String uri;

    @Column(name = "hits", nullable = false)
    @PositiveOrZero(message = "Количество хитов не может быть отрицательным.")
    private Long hits;

}
