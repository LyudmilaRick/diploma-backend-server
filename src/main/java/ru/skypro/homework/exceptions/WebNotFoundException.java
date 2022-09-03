package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Специальное исключения для HTTP ответов.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found Exception")
public class WebNotFoundException extends RuntimeException {

    public WebNotFoundException() {
    }

    public WebNotFoundException(String message) {
        super(message);
    }
    
}
