package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.ResourceException;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Методы управления пользователями сайта.")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = CreateUser.class))),
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @Operation(tags = {"Пользователи"}, summary = "Добавление пользователя", description = "Создаёт на сервере учётную запись нового пользователя.")
    public ResponseEntity addUser(@RequestBody CreateUser body) {
        //TODO: Добавить новую учетную запись пользователя в БД сервера.

        return new ResponseEntity<CreateUser>(body, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperUser.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @Operation(tags = {"Пользователи"}, summary = "Данные пользователя", description = "Запрашивает на сервере информацию о пользователе по его идентификатору.")
    public ResponseEntity getUser(@PathVariable("id") Integer id) {
        //TODO: Прочитать из БД запись пользователя по ключу id.

        throw new ResourceException(HttpStatus.NOT_FOUND, "Запись пользователя '" + id + "' не найдена.");
    }

    @GetMapping("/me")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = ResponseWrapperUser.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @Operation(tags = {"Пользователи"}, summary = "Список пользователей", description = "Запрашивает на сервере информацию о всех зарегистрированных пользователях.")
    public ResponseEntity getUsers() {
        //TODO: Прочитать из БД список всех пользователей.

        ResponseWrapperUser body = new ResponseWrapperUser();
        return new ResponseEntity<ResponseWrapperUser>(body, HttpStatus.OK);
    }

    @PostMapping("/set_password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = NewPassword.class))),
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found")})
    @Operation(tags = {"Пользователи"}, summary = "Новый пароль", description = "Сохраняет новый пароль для зарегистрированного пользователя.")
    public ResponseEntity setPassword(@Valid @RequestBody NewPassword body) {
        //TODO: Изменить пароль в записи пользователя.
        //ERROR: Нужен ID пользователя, либо в запросе, либо в структуре...

        return new ResponseEntity<NewPassword>(body, HttpStatus.OK);
    }

    @PatchMapping("/me")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")})
    @Operation(tags = {"Пользователи"}, summary = "Изменение пользователя", description = "Изменят данные учётной записи зарегистрированного пользователя.")
    public ResponseEntity updateUser(@Valid @RequestBody User body) {

        return new ResponseEntity<User>(body, HttpStatus.OK);
    }
}
