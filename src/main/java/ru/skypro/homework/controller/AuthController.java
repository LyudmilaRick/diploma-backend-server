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
import org.springframework.web.bind.annotation.RestController;

import ru.skypro.homework.dto.LoginReq;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.AuthService;

import static ru.skypro.homework.dto.Role.USER;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "Методы регистрации и аутентификации на сайте.")
public class AuthController {

    private final AuthService authService;

    @Operation(tags={ "Авторизация" }, summary = "Аутентификация", description = "Позволяет пользователю аутентифицироваться в собственном кабинете на сервере.")
//    @ApiResponses(value = { 
//        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema())),
//        @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "*/*", schema = @Schema())),
//        @ApiResponse(responseCode = "401", description = "Unauthorized"),
//        @ApiResponse(responseCode = "403", description = "Forbidden"),
//        @ApiResponse(responseCode = "404", description = "Not Found") })
//    @RequestMapping(value = "/login",
//        produces = { "*/*" }, 
//        consumes = { "application/json" }, 
//        method = RequestMethod.POST)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Parameter(in = ParameterIn.DEFAULT, description = "Данные для авторизации", required=true, schema=@Schema()) @Valid @RequestBody LoginReq body) {
        log.debug("Invoke method login");

        if (authService.login(body.getUsername(), body.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(tags={ "Авторизация" }, summary = "Регистрация", description = "Позволяет новому пользователю зарегистрироваться на сервере.")
//    @ApiResponses(value = { 
//        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*", schema = @Schema())),
//        @ApiResponse(responseCode = "201", description = "Created"),
//        @ApiResponse(responseCode = "401", description = "Unauthorized"),
//        @ApiResponse(responseCode = "403", description = "Forbidden"),
//        @ApiResponse(responseCode = "404", description = "Not Found") })
//    @RequestMapping(value = "/register",
//        produces = { "*/*" }, 
//        consumes = { "application/json" }, 
//        method = RequestMethod.POST)
    @PostMapping("/register")
    public ResponseEntity<?> register(@Parameter(in = ParameterIn.DEFAULT, description = "Данные для регистрации", required=true, schema=@Schema()) @Valid @RequestBody RegisterReq body) {
        log.debug("Invoke method register");

        Role role = body.getRole() == null ? USER : body.getRole();
        if (authService.register(body, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
