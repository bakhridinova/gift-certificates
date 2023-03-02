package com.epam.esm.util;

/**
 * enum for exception messages
 */

public enum ExceptionMessage {
    CERTIFICATE_ID_NOT_FOUND("failed to find certificate with id %s"),
    CERTIFICATE_UPDATE_FAILED("failed to update certificate"),
    CERTIFICATE_CREATE_FAILED("failed to create certificate"),
    CERTIFICATE_DELETE_FAILED("failed to delete certificate"),
    CERTIFICATES_NOT_FOUND("failed to find certificates"),

    TAG_ID_NOT_FOUND("failed to find tag with id %s"),
    TAG_CREATE_FAILED("failed to create tag"),
    TAG_DELETE_FAILED("failed to delete tag"),
    TAGS_NOT_FOUND("failed to find tags");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
