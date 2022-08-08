package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

/**
 * Контроллер обработки запросов по пользователям.
 */
@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Методы управления пользователями сайта.")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @Operation(tags = {"Пользователи"}, summary = "Добавление пользователя", description = "Создаёт на сервере учётную запись нового пользователя.")
    public ResponseEntity<CreateUser> addUser(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового пользователя", required = true, schema = @Schema()) @Valid @RequestBody CreateUser body) {
        log.info("Invoke: {0}({1})", "addUser", body);
        return new ResponseEntity<>(userService.addUser(body), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(tags = {"Пользователи"}, summary = "Данные пользователя", description = "Запрашивает на сервере информацию о пользователе по его идентификатору.")
    public ResponseEntity<User> getUser(@Parameter(in = ParameterIn.PATH, description = "Идентификатор пользоателя", required = true, schema = @Schema()) @PathVariable("id") Integer usrKey) {
        log.info("Invoke: {0}({1})", "getUser", usrKey);
        return new ResponseEntity<>(userService.getUser(usrKey), HttpStatus.OK);
    }

    @GetMapping("/me")
    @Operation(tags = {"Пользователи"}, summary = "Список пользователей", description = "Запрашивает на сервере информацию о всех зарегистрированных пользователях.")
    public ResponseEntity<ResponseWrapperUser> getUsers() {
        log.info("Invoke: {0}()", "getUsers");
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PatchMapping("/me")
    @Operation(tags = {"Пользователи"}, summary = "Изменение пользователя", description = "Изменят данные учётной записи зарегистрированного пользователя.")
    public ResponseEntity<User> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "Изменённые данные пользователя", required = true, schema = @Schema()) @Valid @RequestBody User body) {
        log.info("Invoke: {0}()", "updateUser");
        return new ResponseEntity<>(userService.updateUser(body), HttpStatus.OK);
    }

    @PostMapping("/set_password")
    @Operation(tags = {"Пользователи"}, summary = "Новый пароль", description = "Сохраняет новый пароль для зарегистрированного пользователя.")
    public ResponseEntity<NewPassword> setPassword(@Parameter(in = ParameterIn.DEFAULT, description = "Данные для смены пароля", required = true, schema = @Schema()) @Valid @RequestBody NewPassword body, Authentication auth) {
        log.info("Invoke: {0}()", "setPassword");
        
        if (auth != null && auth.isAuthenticated()) {
            NewPassword resultPassword = userService.setPassword(auth.getName(), body.getCurrentPassword(), body.getNewPassword()); 
            return new ResponseEntity<>(resultPassword, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
