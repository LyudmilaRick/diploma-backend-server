package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skypro.homework.models.AdsEntity;

/**
 * Сущность, описывающая структуру данных объявления.
 */
@Data
@Schema(description = "Данные объявления")
public class Ads {

    @Schema(description = "Первичный ключ записи объявления")
    private Integer pk;
    @Schema(description = "Идентификатор автора объявления")
    private Integer author;
    @Schema(description = "Объявленная цена за товар")
    private Integer price;
    @Schema(description = "Заголовок объявления")
    private String title;
    @Schema(description = "Ссылка для скачивания содержимого картинки")
    private String image;
    
}
