package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * Оболочка тела ответа на запросы по комментариям к объявлению.
 */
@Data
@Schema(description = "Оболочка ответа по комментариям к объявлению")
public class ResponseWrapperComment {
    @Schema(description = "Количество записей")
    private Integer count;
    @Schema(description = "Список данных пользователя")
    private List<AdsComment> results;
}
