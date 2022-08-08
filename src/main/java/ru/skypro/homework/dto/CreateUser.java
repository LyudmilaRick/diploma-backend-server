package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Структура данных тела запроса на создание учетной записи пользователя.
 */
@Data
@Schema(description = "Структура тела запроса создание записи пользователя")
public class CreateUser {

    @Schema(description = "Адрес электронной почты (логин пользователя)")
    private String email;
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Schema(description = "Пароль доступа к кабинету пользователя")
    private String password;
    @Schema(description = "Контактный телефон пользователя")
    private String phone;

}
