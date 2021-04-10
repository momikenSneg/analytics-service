package ru.nsu.internship.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.I_AM_A_TEAPOT, reason="Message sending failed")
public class SendFailedException extends Exception{
    public SendFailedException(){super();}
}
