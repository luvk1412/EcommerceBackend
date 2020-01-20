package application.exception;

import application.model.HttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(AppException.class)
    public HttpResponse UnauthorisedUserException(AppException exception){
        return new HttpResponse(exception.getStatusCode(), exception.getMessage());
    }
}
