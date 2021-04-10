package ru.nsu.internship.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Template has no recipients")
public class NoRecipientsException extends Exception {
    public NoRecipientsException(String errorMessage) {
        super(errorMessage);
    }
    public NoRecipientsException(){super();}
}
