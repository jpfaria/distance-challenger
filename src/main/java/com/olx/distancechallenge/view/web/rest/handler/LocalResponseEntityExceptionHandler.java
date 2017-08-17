package com.olx.distancechallenge.view.web.rest.handler;

import com.olx.distancechallenge.domain.exception.WordConflictException;
import com.olx.distancechallenge.domain.exception.WordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class LocalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({WordNotFoundException.class})
    protected void notfound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({WordConflictException.class})
    protected void conflict(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

}
