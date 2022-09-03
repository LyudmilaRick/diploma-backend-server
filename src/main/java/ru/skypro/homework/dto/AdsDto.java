package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс Объекта Передачи Данных, описывающий структуру данных объявления.
 * <p>
 * DTO（Data Transfer Object）
 *
 * @see "https://ru.wikipedia.org/wiki/DTO"
 * @see "https://askdev.ru/q/soglashenie-ob-imenovanii-obektov-peredachi-dannyh-java-117705/"
 * <p>
 * Не следует использовать lombok.Data, он лишь создаёт проблемы в DTO объектах!
 * @see "https://stackoverflow.com/questions/51820133/using-data-annotation-on-java-dto-class"
 */
@Data
@Schema(description = "Данные объявления")
public class AdsDto {

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
