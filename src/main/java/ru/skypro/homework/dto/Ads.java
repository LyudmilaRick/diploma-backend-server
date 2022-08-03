package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Сущность, описывающая структуру данных объявления.
 */
@Data
@Schema(description = "Данные объявления")
public class Ads {

    @Schema(description = "Идентификатор автора объявления")
    private Integer author;
    @Schema(description = "Содержимое картинки к объявлению")
    private String image;
    @Schema(description = "Первичный ключ записи объявления")
    private Integer pk;
    @Schema(description = "Объявленная цена за товар")
    private Integer price;
    @Schema(description = "Заголовок объявления")
    private String title;
}
