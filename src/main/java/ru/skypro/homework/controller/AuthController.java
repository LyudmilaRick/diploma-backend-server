package ru.skypro.homework.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.skypro.homework.dto.LoginReqDto;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.AuthService;

import static ru.skypro.homework.dto.Role.USER;

/**
 * Контроллер обработки запросов аутентификации и регистрации новых пользователей.
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true",
        allowedHeaders = "*", methods = {RequestMethod.POST})
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "Методы регистрации и аутентификации на сайте.")
public class AuthController {

    private final AuthService authService;

    /**
     * Обработка POST запроса аутентификации.
     * <p>
     * Позволяет пользователю аутентифицироваться в собственном кабинете на сервере.
     *
     * @param body тело запроса, описанное сущностью {
     * @see LoginReqDto}.
     * @return объект ответа, содержащий соответствующую протоколу сущность.
     */
    @PostMapping("/login")
    @Operation(tags = {"Авторизация"}, summary = "Аутентификация", description = "Позволяет пользователю аутентифицироваться в собственном кабинете на сервере.")
    public ResponseEntity<?> login(@Parameter(in = ParameterIn.DEFAULT, description = "Данные для авторизации", required = true, schema = @Schema()) @Valid @RequestBody LoginReqDto body) {
        log.info("Invoke: {}({})", "login", body.getUsername());

        if (authService.login(body.getUsername(), body.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Обработка POST запроса регистрации пользователя.
     * <p>
     * Позволяет новому пользователю зарегистрироваться на сервере.
     *
     * @param body тело запроса, описанное сущностью {
     * @see RegisterReqDto}.
     * @return объект ответа, содержащий соответствующую протоколу сущность.
     */
    @PostMapping("/register")
    @Operation(tags = {"Авторизация"}, summary = "Регистрация", description = "Позволяет новому пользователю зарегистрироваться на сервере.")
    public ResponseEntity<?> register(@Parameter(in = ParameterIn.DEFAULT, description = "Данные для регистрации", required = true, schema = @Schema()) @Valid @RequestBody RegisterReqDto body) {
        log.info("Invoke: {}({})", "register", body.getUsername());

        Role role = body.getRole() == null ? USER : body.getRole();
        if (authService.register(body, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
