package io.zipcoder.tc_spring_poll_application.exceptions;

import dtos.error.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpStatus;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe,
                                                                 HttpServletRequest request) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTimeStamp(new Date().getTime());
            errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
            errorDetail.setTitle("Resource Not Found");
            errorDetail.setDetail(rnfe.getMessage());
            errorDetail.setDeveloperMessage(rnfe.getClass().getName());

            return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
        }
}
