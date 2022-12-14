package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс контроллера для перехвата необработанных исключений.
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(value=HttpStatus.NOT_IMPLEMENTED, reason = "Requested operation is not supported.")
    @ExceptionHandler(UnsupportedOperationException.class)
    public void handleException(HttpServletRequest req, UnsupportedOperationException e) {
        log.error("Request: " + req.getRequestURL() + " raised: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised", ex);
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
