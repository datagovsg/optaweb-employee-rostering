package org.optaweb.employeerostering.exception;

/**
 * Thrown when the email service encounters an error.
 */
public class EmailServiceException extends Exception {
    public EmailServiceException (String message, Throwable cause) {
        super(message, cause);
    }
}
