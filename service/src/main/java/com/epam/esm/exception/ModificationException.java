package com.epam.esm.exception;

import com.epam.esm.util.exception.ExceptionMessage;

public class ModificationException extends Exception {
    public ModificationException(ExceptionMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
