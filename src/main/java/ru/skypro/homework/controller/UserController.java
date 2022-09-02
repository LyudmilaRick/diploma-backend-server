package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import java.util.ArrayList;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.ResponseWrapperUserDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import static ru.skypro.homework.models.Constants.*;

/**
 * Контроллер обработки запросов по пользователям сайта.
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true",
        allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE})
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Методы управления пользователями сайта.")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * В постановке отсутствует. Добавлен для самоконтроля и тестирования работы с ролями
     */
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @Operation(tags = {"Пользователи"}, summary = "Список пользователей", description = "Запрашивает на сервере список всех зарегистрированных пользователей.")
    public ResponseEntity<ResponseWrapperUserDto> getUsers() {
        log.info("Invoke: {}()", "getUsers");
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(tags = {"Пользователи"}, summary = "Добавление пользователя", description = "Создаёт на сервере учётную запись нового пользователя.")
    protected ResponseEntity<CreateUser> addUser(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового пользователя", required = true, schema = @Schema()) @Valid @RequestBody CreateUser body) {
        log.info(INVOKE_STR_1, "addUser", body);
        return new ResponseEntity<>(userService.addUser(body), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(tags = {"Пользователи"}, summary = "Данные пользователя", description = "Запрашивает на сервере информацию о пользователе по его идентификатору.")
    public ResponseEntity<UserDto> getUser(@Parameter(in = ParameterIn.PATH, description = "Идентификатор пользоателя", required = true, schema = @Schema()) @PathVariable("id") Integer usrKey) {
        log.info(INVOKE_STR_1, "getUser", usrKey);

        return new ResponseEntity<>(userService.getUser(usrKey), HttpStatus.OK);

        /*
            NOTE: Возможно, эта функция должна быть доступна только администратору?
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                String username = auth.getName();
                if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    return new ResponseEntity<>(userService.getUser(usrKey), HttpStatus.OK);
                } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
         */
    }

    @GetMapping("/me")
    @Operation(tags = {"Пользователи"}, summary = "Список пользователей", description = "Запрашивает на сервере информацию о всех зарегистрированных пользователях.")
    public ResponseEntity<ResponseWrapperUserDto> getUsers(Authentication auth) {
        log.info(INVOKE_STR_1, "getUsers", auth.getName());

        //TODO: Как можно получить список собственных пользователей, если username, по логике, должен быть уникальным???
        ArrayList<UserDto> list = new ArrayList<>();
        list.add(userService.getUser(auth.getName()));

        ResponseWrapperUserDto result = new ResponseWrapperUserDto();
        result.setResults(list);
        result.setCount(list.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/me")
    @Operation(tags = {"Пользователи"}, summary = "Изменение пользователя", description = "Изменят данные учётной записи зарегистрированного пользователя.")
    public ResponseEntity<UserDto> updateUser(Authentication auth, @Parameter(in = ParameterIn.DEFAULT, description = "Изменённые данные пользователя", required = true, schema = @Schema()) @Valid @RequestBody UserDto body) {
        log.info(INVOKE_STR_1, "updateUser", auth.getName());
        return new ResponseEntity<>(userService.updateUser(auth.getName(), body), HttpStatus.OK);
    }

    @PostMapping("/set_password")
    @Operation(tags = {"Пользователи"}, summary = "Новый пароль", description = "Сохраняет новый пароль для зарегистрированного пользователя.")
    public ResponseEntity<NewPasswordDto> setPassword(Authentication auth, @Parameter(in = ParameterIn.DEFAULT, description = "Данные для смены пароля", required = true, schema = @Schema()) @Valid @RequestBody NewPasswordDto body) {
        log.info("Invoke: {}({})", "setPassword", auth.getName());

        if (authService.setPassword(auth.getName(), body.getCurrentPassword(), body.getNewPassword())) {
            return new ResponseEntity<>(userService.newPassword(body.getCurrentPassword(), body.getNewPassword()),
                    HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
