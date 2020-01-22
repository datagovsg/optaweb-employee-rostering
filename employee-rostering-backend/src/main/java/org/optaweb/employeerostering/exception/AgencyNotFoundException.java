package org.optaweb.employeerostering.exception;

/**
 * Thrown when an Agency could not be found.
 */
public class AgencyNotFoundException extends Exception {
    public AgencyNotFoundException (String message) {
        super(message);
    }
}
