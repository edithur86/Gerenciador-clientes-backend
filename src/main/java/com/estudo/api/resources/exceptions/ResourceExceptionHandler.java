package com.estudo.api.resources.exceptions;

import com.estudo.api.commons.Translator;
import com.estudo.api.services.exceptions.AuthorizationException;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        StandartError err = StandartError.builder()
                .msg(e.getMessage())
                .code(e.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandartError> authorizationException(AuthorizationException e, HttpServletRequest request) {

        StandartError err = StandartError.builder()
                .msg(e.getMessage())
                .code(e.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(BeanValidationException.class)
    public ResponseEntity<StandartError> BeanValidationException(BeanValidationException e, HttpServletRequest request) {

        StandartError err = null;
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            String message = Translator.toLocale(constraintViolation.getMessageTemplate());
            if (constraintViolation.getMessageTemplate().replaceAll("[{}]", "").equals(message))
                message = constraintViolation.getMessage();
            if (err == null) {
                err = StandartError.builder()
                        .msg(message)
                        .code(message)
                        .timeStamp(System.currentTimeMillis())
                        .detalhes(new ArrayList<>())
                        .build();
            }
            StandartErrorDetails details = StandartErrorDetails.builder()
                    .code(message)
                    .message(message)
                    .build();
            err.getDetalhes().add(details);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
