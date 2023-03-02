package com.epam.esm.exception;

import com.epam.esm.reponse.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * handler for exceptions thrown in CertificateController and TagController
 */

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandler {
    /**
     * handler method for InvalidRequestBodyException
     *
     * @param ex InvalidRequestBodyException
     * @return instance of ResponseData with violations, bad request status and failure message
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseData<Object[]> handleInvalidRequestBodyException(InvalidRequestBodyException ex) {
        log.info("********** sending error message...");
        return new ResponseData<>(ex.getViolations(), "invalid request body",  HttpStatus.BAD_REQUEST);
    }

    /**
     * handler method for NotFoundException
     *
     * @param ex NotFoundException
     * @return instance of ResponseData with failure message and bad request status
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseData<Object> handleNotFoundException(NotFoundException ex) {
        log.info("********** sending error message...");
        return new ResponseData<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * handler method for ModificationException
     *
     * @param ex ModificationException
     * @return instance of ResponseData with failure message and bad request status
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ModificationException.class)
    public ResponseData<Object> handleModificationException(ModificationException ex) {
        log.info("********** sending error message...");
        return new ResponseData<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
