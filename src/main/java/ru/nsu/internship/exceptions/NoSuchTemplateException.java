package ru.nsu.internship.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="There is no such template")
public class NoSuchTemplateException extends Exception {
    public NoSuchTemplateException(String m){super(m);}
    public NoSuchTemplateException(){super();}
}
