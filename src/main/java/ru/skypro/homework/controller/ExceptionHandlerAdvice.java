package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skypro.homework.ResourceException;

/**
 * Контроллер общей обработки исключительных ситуаций.
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity handleException(ResourceException e) {
        //TODO: log exception 
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
