package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Структура данных используемая для создания нового объявления.
 */
@Data
@Schema(description = "Данные для создания объявления")
public class CreateAds {

    @Schema(description = "Текст описания товара по объявлению")
    private String description;
    @Schema(description = "Ссылка для скачивания содержимого картинки")
    private String image;
    @Schema(description = "Первичный ключ записи объявления")
    private Integer pk;
    @Schema(description = "Объявленная цена за товар")
    private Integer price;
    @Schema(description = "Заголовок объявления")
    private String title;
}
