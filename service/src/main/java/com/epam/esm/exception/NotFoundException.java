package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessage;

public class NotFoundException extends Exception {
    public NotFoundException(ExceptionMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public NotFoundException(ExceptionMessage message, long id) {
        super(String.format(message.getMessage(), id));
    }
}
