package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * Оболочка тела ответа на запросы по объявлениям.
 */
@Data
@Schema(description = "Оболочка ответа о данных объявления")
public class ResponseWrapperAds {
    @Schema(description = "Количество записей")
    private Integer count;
    @Schema(description = "Список данных пользователя")
    private List<Ads> results;
}
