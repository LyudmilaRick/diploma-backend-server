package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс сущности, описывающей данные пользователя.
 */
@Data
@Schema(description = "Сущность пользователя")
public class UserDto {

    @Schema(description = "Идентификатор пользователя (ключ)")
    private Integer id;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Адрес электронной почты")
    private String email;
    @Schema(description = "Телефон пользователя")
    private String phone;

}
