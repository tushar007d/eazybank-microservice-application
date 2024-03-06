package com.tushar.accounts.exception;

import com.tushar.accounts.dto.ErrorResponseDto;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
-   The @ControllerAdvice annotation is used to mark the class as REST Controller Advice
-   This is used to handle Exceptions globally
-   @RestControllerAdvice = @ControllerAdvice + @ResponseBody
-   In Java Spring Boot applications, the @ControllerAdvice annotation is a powerful tool for centralizing exception
    handling across multiple controllers. It acts as a global interceptor that applies advice (exception handling logic)
    to controllers annotated with @RequestMapping.

-   We have extended ResponseEntityExceptionHandler class to override the "handleMethodArgumentNotValid" method
    handleMethodArgumentNotValid method is a  method in the ResponseEntityExceptionHandler class that is called when a
    method argument is not valid. It takes in a MethodArgumentNotValidException object as a parameter, which is
    the exception that is thrown when a method argument is not valid.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    /*
    As Exception.class is the parent for all the Exception including Checked and Unchecked
    so we can handle all the exception except by this handler.
    it will eliminate below exceptions : CustomerAlreadyExistException & ResourceNotFoundException
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<ErrorResponseDto> (errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException
            (CustomerAlreadyExistException exception, WebRequest request){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
                            ResourceNotFoundException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    /*
-   We have extended ResponseEntityExceptionHandler class to override the "handleMethodArgumentNotValid" method
    handleMethodArgumentNotValid method is a  method in the ResponseEntityExceptionHandler class that is called when a
    method argument is not valid. It takes in a MethodArgumentNotValidException object as a parameter, which is
    the exception that is thrown when a method argument is not valid.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //here we collecting all the errors in the validationErrorsList. Then by iterating we are mapping their
        //fieldName with the error message
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> validationErrorsList = ex.getBindingResult().getAllErrors();
        validationErrorsList.forEach((error) -> {
            String field = ((FieldError)error).getField();
            String validationMessage = error.getDefaultMessage();
            errors.put(field, validationMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
