package org.optaweb.employeerostering.exception;

/**
 * Thrown when OTP class cannot be instantiated.
 */
public class OtpInitRuntimeException extends RuntimeException {
    public OtpInitRuntimeException (String message, Throwable cause) {
        super(message, cause);
    }
}
