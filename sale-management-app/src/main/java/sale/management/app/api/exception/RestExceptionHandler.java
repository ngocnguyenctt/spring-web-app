package sale.management.app.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author ngoc on 30/04/2018
 * @subject sale-management-app
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex)
    {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex)
    {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        String errorMessage = localizeErrorMessage(ex.getMessage().split(":")[1]
                .substring(2, ex.getMessage().split(":")[1].length() - 1));
        apiError.setMessage(errorMessage);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation errors");
        List<FieldError> errorList = ex.getFieldErrors();
        List<ApiValidationError> apiValidationErrorList = new ArrayList<>();
        errorList.forEach(fieldError ->
        {
            ApiValidationError apiValidationError = new ApiValidationError(
                    fieldError.getObjectName(),
                    fieldError.getField(), fieldError.getRejectedValue(),
                    localizeErrorMessage(fieldError.getCodes()[0])
            );
            apiValidationErrorList.add(apiValidationError);
        });
        apiError.setFieldErrors(apiValidationErrorList);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private String localizeErrorMessage(String errorCode)
    {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(errorCode, new Object[]{}, locale);
    }
}
