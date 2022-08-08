package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Сущность, описывающая структуру данных комментария к объявлению.
 */
@Data
@Schema(description = "Данные комментария к объявлению")
public class AdsComment {

    @Schema(description = "Первичный ключ записи объявления")
    private Integer pk;
    @Schema(description = "Идентификатор автора комментария")
    private Integer author;
    @Schema(description = "Дата и время создания комментария")
    private OffsetDateTime createdAt;
    @Schema(description = "Текст комментария")
    private String text;
}
