package com.somethingsblog.app.ws.exceptions;

import com.somethingsblog.app.ws.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {
    // now any time that a UserServiceException is thrown this method handles it
    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());// using this model
        // to give the api user a nice json model to rep what just happened
//        {
//            "timestamp": "2022-05-12T10:54:22.117+00:00",
//                "message": "Missing required field. Please check documentation for required fields"
//        }
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // handle all other types of exceptions
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());// using this model
        // to give the api user a nice json model to rep what just happened
//        {
//            "timestamp": "2022-05-12T10:54:22.117+00:00",
//                "message": "Missing required field. Please check documentation for required fields"
//        }
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
