package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Специальное исключения для HTTP ответов.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict Exception")
public class WebConflictException extends RuntimeException {

    public WebConflictException() {
    }

    public WebConflictException(String message) {
        super(message);
    }

}
