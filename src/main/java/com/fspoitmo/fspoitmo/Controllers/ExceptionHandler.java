package com.fspoitmo.fspoitmo.Controllers;

import com.fspoitmo.fspoitmo.Exceptions.ErrorResult;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;


@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
    protected ResponseEntity<ErrorResult> handleUserException(UserException us) {
        String code = us.getCode();
        switch (code) {
            case "400":
                return new ResponseEntity<>(new ErrorResult(us.getId(), code, us.getMessage(), us.getData()), HttpStatus.BAD_REQUEST);
            case "403":
                return new ResponseEntity<>(new ErrorResult(us.getId(), code, us.getMessage(), us.getData()), HttpStatus.FORBIDDEN);
            case "404":
                return new ResponseEntity<>(new ErrorResult(us.getId(), code, us.getMessage(), us.getData()), HttpStatus.NOT_FOUND);
            case "406":
                return new ResponseEntity<>(new ErrorResult(us.getId(), code, us.getMessage(), us.getData()), HttpStatus.NOT_ACCEPTABLE);
            default:
                return new ResponseEntity<>(new ErrorResult(us.getId(), code, us.getMessage(), us.getData()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResult> handleFoundException(NoHandlerFoundException exception) {
        return new ResponseEntity<>(new ErrorResult(HttpStatus.NOT_IMPLEMENTED.value(), "501", "internal_server_error", "нет такоВа ендпоинта"), HttpStatus.NOT_IMPLEMENTED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<ErrorResult> handleInternalErrorException(ServerErrorException exception) {
        return new ResponseEntity<>(new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal_server_error", "", Arrays.toString(exception.getStackTrace())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchFieldException.class)
    protected ResponseEntity<ErrorResult> handleNoSuchFieldException(NoSuchFieldException exception) {
        return new ResponseEntity<>(new ErrorResult(404, "not_found", "Field " + exception.getMessage() + "  doesn't exist", ""),  HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResult> handleOtherException(Exception exception) {
        return new ResponseEntity<>(new ErrorResult(500, "internal_server_error", exception.getMessage(), Arrays.toString(exception.getStackTrace())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
