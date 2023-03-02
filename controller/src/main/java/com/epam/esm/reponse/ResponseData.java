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
     * @param status HttpStatus response status
     * @param message String response message
     */
    public ResponseData(T content, HttpStatus status, String message) {
        this.content = content;
        this.status = status;
        this.message = message;
    }

    /**
     * used for responses with no content
     *
     * @param status HttpStatus response status
     * @param message String response message
     */
    public ResponseData(HttpStatus status, String message) {
        this.content = null;
        this.status = status;
        this.message = message;
    }

    /**
     * used for responses with no message
     *
     * @param content T response body
     */
    public ResponseData(T content) {
        this.content = content;
    }
}
