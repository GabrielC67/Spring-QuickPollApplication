package io.zipcoder.tc_spring_poll_application.exceptions;

import dtos.error.ErrorDetail;
import dtos.error.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ResponseEntityExceptionHandler responseEntityExceptionHandler;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException manve, HttpServletRequest request){
        ErrorDetail errorDetail = new ErrorDetail();

        List<FieldError> fieldErrors =  manve.getBindingResult().getFieldErrors();
        for(FieldError fe : fieldErrors) {

            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
            if(validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorDetail.getErrors().put(fe.getField(), validationErrorList);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe, null));
            validationErrorList.add(validationError);

            return new ResponseEntity<>(validationErrorList, HttpStatusCode.valueOf(400));
        }
        return null;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
