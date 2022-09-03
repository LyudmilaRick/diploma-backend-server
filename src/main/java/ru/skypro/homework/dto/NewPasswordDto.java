package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Структура данных для смены пароля.
 */
@Data
@Schema(description = "Данные для смены пароля")
public class NewPasswordDto {

    @Schema(description = "Текущий пароль")
    private String currentPassword;
    @Schema(description = "Значение нового пароля")
    private String newPassword;

}
