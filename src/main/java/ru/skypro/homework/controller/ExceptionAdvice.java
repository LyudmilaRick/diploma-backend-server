package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс контроллера для перехвата необработанных исключений.
 * <p>
 * Перехватчики исключений, размещенные в этом классе, видит Swagger и генерирует по ним описание на своих страницах.
 * <p>
 * Классы собственных исключений, имеющие нотацию <b>@ResponseStatus</b>, но не прописанные в этом классе, не попадают в
 * Swagger документацию.
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    /*
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal Argument Exception")
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleException(HttpServletRequest req, IllegalArgumentException e) {
        log.error(getMessageTitle(req, e));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found Exception")
    @ExceptionHandler(NullPointerException.class)
    public void handleException(HttpServletRequest req, NullPointerException e) {
        log.error(getMessageTitle(req, e));
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict Exception")
    @ExceptionHandler(TypeNotPresentException.class)
    public void handleException(HttpServletRequest req, TypeNotPresentException e) {
        log.error(getMessageTitle(req, e));
    }
     */
    /**
     * Глобальный обработчик исключений, возникающих в контроллерах приложения.
     *
     * @param req объект {
     * @see HttpServletRequest}, содержащий информацию о запросе клиента.
     * @param e объект перехваченного исключения.
     */
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Requested operation is not supported.")
    @ExceptionHandler(UnsupportedOperationException.class)
    public void handleException(HttpServletRequest req, UnsupportedOperationException e) {
        log.error(getMessageTitle(req, e));
    }

    /**
     * Глобальный обработчик исключений, возникающих в контроллерах приложения.
     *
     * @param req объект {
     * @see HttpServletRequest}, содержащий информацию о запросе клиента.
     * @param e объект перехваченного исключения.
     */
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data Integrity Violation Exception.")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleException(HttpServletRequest req, DataIntegrityViolationException e) {
        log.error(getMessageTitle(req, e));
    }

    /**
     * Глобальный обработчик исключений, возникающих в контроллерах приложения.
     *
     * @param req объект {
     *
     * @return  @see HttpServletRequest}, содержащий информацию о запросе клиента.
     * @param e объект перехваченного исключения.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(HttpServletRequest req, Exception e) {
        log.error(getMessageTitle(req), e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    private static String getMessageTitle(HttpServletRequest req) {
        return "Request: " + req.getMethod() + " " + req.getRequestURL() + " raised: ";
    }

    private static String getMessageTitle(HttpServletRequest req, Exception e) {
        return "Request: " + req.getMethod() + " " + req.getRequestURL() + " raised: " + e.getMessage();
    }
}
