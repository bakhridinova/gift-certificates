package com.epam.esm.exception;

/**
 * exception thrown when request body validation failed
 */

public class InvalidRequestBodyException extends Exception {
    private final Object[] violations;

    /**
     * array of objects containing error messages
     * see usage for more info
     *
     * @param violations Object[]
     */
    public InvalidRequestBodyException(Object[] violations) {
        this.violations = violations;
    }

    public Object[] getViolations() {
        return violations;
    }
}
