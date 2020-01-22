package org.optaweb.employeerostering.exception;

/**
 * Thrown when OTP could not be created.
 */
public class OtpCreationException extends Exception {
    public OtpCreationException (String message, Throwable cause) {
        super(message, cause);
    }
}
