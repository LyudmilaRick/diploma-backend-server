package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Специальное исключения для HTTP ответов.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request Exception")
public class WebBadRequestException extends RuntimeException {

    public WebBadRequestException() {
    }

    public WebBadRequestException(String message) {
        super(message);
    }
    
}
