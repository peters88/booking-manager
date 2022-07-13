package com.psayol.bookingmanager.exception.handler;

import com.psayol.bookingmanager.exception.InvalidDatesException;
import com.psayol.bookingmanager.exception.UnableToProcessRequestException;
import com.psayol.bookingmanager.response.ResponseConstants;
import com.psayol.bookingmanager.response.ResponseDataDTO;
import com.psayol.bookingmanager.response.builder.ResponseDataBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.regex.Pattern;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final Pattern BLOCK_CONSTRAINT = Pattern.compile(".*OPTIMISTIC_LOCK.*");
    private static final String OPTIMISTIC_LOCK_EXCEPTION = "OPTIMISTIC_LOCK";

    /***
     * Generic exception handler
     *
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(UnableToProcessRequestException.class)
    public final ResponseEntity<Object> unableToProcessRequestException(UnableToProcessRequestException ex) {
        logger.error("UnableToProcessRequestException: ", ex);

        ResponseDataDTO response = new ResponseDataBuilder()
                .setApiVersion("v1")
                .setStatusMessage(ResponseConstants.FAILURE.getMessage())
                .setStatusCode(ResponseConstants.FAILURE.getCode())
                .setError(new ResponseDataBuilder.ErrorBuilder()
                        .setCode(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getCode())
                        .setMessage(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage())
                        .build())
                .build();

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }


    /**
     *  When constraintViolationException is thrown and constrainName contains "OPTIMISTIC_LOCK" a "Dates Already Booked" error response is returned meaning
     *  the days trying to be blocked are no longer available
     *
     *  When constraintViolationException is thrown and constrainName not contains "OPTIMISTIC_LOCK" a generic error is returned meaning
     *  the error is due another unique constraint
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        logger.error("ConstraintViolationException: ", ex);

        ResponseDataDTO response;

//        if(BLOCK_CONSTRAINT.matcher(ex.getConstraintName()).matches()){
        if(ex.getConstraintName().contains(OPTIMISTIC_LOCK_EXCEPTION)){
            response = new ResponseDataBuilder()
                    .setApiVersion("v1")
                    .setStatusMessage(ResponseConstants.FAILURE.getMessage())
                    .setStatusCode(ResponseConstants.FAILURE.getCode())
                    .setError(new ResponseDataBuilder.ErrorBuilder()
                            .setCode(ResponseConstants.CONSTRAINT_VIOLATION_EXCEPTION.getCode())
                            .setMessage(ResponseConstants.CONSTRAINT_VIOLATION_EXCEPTION.getMessage())
                            .build())
                    .build();

        }else{
            response = new ResponseDataBuilder()
                    .setApiVersion("v1")
                    .setStatusMessage(ResponseConstants.FAILURE.getMessage())
                    .setStatusCode(ResponseConstants.FAILURE.getCode())
                    .setError(new ResponseDataBuilder.ErrorBuilder()
                            .setCode(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getCode())
                            .setMessage(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage())
                            .build())
                    .build();

        }

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    /***
     * Generic exception handler
     *
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidDatesException.class)
    public final ResponseEntity<Object> invalidDateException(InvalidDatesException ex) {
        logger.error("InvalidDatesException: ", ex);

        ResponseDataDTO response = new ResponseDataBuilder()
                .setApiVersion("v1")
                .setStatusMessage(ResponseConstants.FAILURE.getMessage())
                .setStatusCode(ResponseConstants.FAILURE.getCode())
                .setError(new ResponseDataBuilder.ErrorBuilder()
                        .setCode(ResponseConstants.INVALID_DATE_EXCEPTION.getCode())
                        .setMessage(ResponseConstants.INVALID_DATE_EXCEPTION.getMessage())
                        .build())
                .build();

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

}
