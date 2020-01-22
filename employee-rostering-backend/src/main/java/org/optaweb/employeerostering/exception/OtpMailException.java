package org.optaweb.employeerostering.exception;

/**
 * Thrown when OTP could not be mailed to the user trying to login.
 */
public class OtpMailException extends Exception {
    public OtpMailException (String message, Throwable cause) {
        super(message, cause);
    }
}
