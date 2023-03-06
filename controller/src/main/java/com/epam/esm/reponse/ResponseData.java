package com.epam.esm.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * custom class used as return type of controller methods
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    private HttpStatus status;
    private String message;
    private T content;

    /**
     * used for handling InvalidRequestBodyException
     *
     * @param content T response body
     * @param message String response message
     * @param status HttpStatus response status
     */
    public ResponseData(T content, String message, HttpStatus status) {
        this.content = content;
        this.message = message;
        this.status = status;
    }

    /**
     * used for responses with no content
     *
     * @param message String response message
     * @param status HttpStatus response status
     */
    public ResponseData(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    /**
     * used for responses with no message
     *
     * @param content T response body
     * @param status HttpStatus response status
     */
    public ResponseData(T content, HttpStatus status) {
        this.content = content;
        this.status = status;
    }
}
