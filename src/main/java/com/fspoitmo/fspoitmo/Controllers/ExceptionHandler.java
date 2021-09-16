package com.fspoitmo.fspoitmo.Controllers;

import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Exceptions.UserExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.HashMap;


@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
    protected ResponseEntity<UserException> handleUserException(UserException ex) {
        return new ResponseEntity<>(ex, ex.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<UserException> handleFoundException(NoHandlerFoundException exception) {
        // Костыль чтоб не словить пустой ответ из-за ошибки сериализации
        HashMap<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", exception.getMessage());
        debugInfo.put("stackTrace", exception.getStackTrace());

        UserException ex = new UserException(UserExceptionType.OBJECT_NOT_FOUND, debugInfo);
        return handleUserException(ex);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<UserException> handleInternalErrorException(ServerErrorException exception) {
        // Костыль чтоб не словить пустой ответ из-за ошибки сериализации
        HashMap<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", exception.getMessage());
        debugInfo.put("stackTrace", exception.getStackTrace());


        UserException ex = new UserException(UserExceptionType.SERVER_ERROR, debugInfo);
        return handleUserException(ex);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchFieldException.class)
    protected ResponseEntity<UserException> handleNoSuchFieldException(NoSuchFieldException exception) {
        // Костыль чтоб не словить пустой ответ из-за ошибки сериализации
        HashMap<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", exception.getMessage());
        debugInfo.put("stackTrace", exception.getStackTrace());


        UserException ex = new UserException(UserExceptionType.OBJECT_NOT_FOUND, debugInfo);
        return handleUserException(ex);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<UserException> handleBadRequestException(HttpClientErrorException exception) {
        // Костыль чтоб не словить пустой ответ из-за ошибки сериализации
        HashMap<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", exception.getMessage());
        debugInfo.put("stackTrace", exception.getStackTrace());

        UserException ex = new UserException(UserExceptionType.BAD_REQUEST, debugInfo);
        return handleUserException(ex);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected ResponseEntity<UserException> handleOtherException(Exception exception) {
        // Костыль чтоб не словить пустой ответ из-за ошибки сериализации
        HashMap<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", exception.getMessage());
        debugInfo.put("stackTrace", exception.getStackTrace());

        UserException ex = new UserException(UserExceptionType.SERVER_ERROR, debugInfo);
        return handleUserException(ex);
    }
}