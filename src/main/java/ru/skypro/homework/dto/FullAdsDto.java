package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Структура данных, описывающая данные объявления с информацией об авторе, создавшем его.
 */
@Data
@Schema(description = "Полная информация об объявлении и его авторе")
public class FullAdsDto {

    @Schema(description = "Имя автора объявления")
    private String authorFirstName;
    @Schema(description = "Фамилия автора объявления")
    private String authorLastName;
    @Schema(description = "Текст описания товара по объявлению")
    private String description;
    @Schema(description = "Адрес электронной почты автора объявления")
    private String email;
    @Schema(description = "Ссылка для скачивания содержимого картинки")
    private String image;
    @Schema(description = "Контактный телефон автора объявления")
    private String phone;
    @Schema(description = "Первичный ключ записи объявления")
    private Integer pk;
    @Schema(description = "Объявленная цена за товар")
    private Integer price;
    @Schema(description = "Заголовок объявления")
    private String title;

}
