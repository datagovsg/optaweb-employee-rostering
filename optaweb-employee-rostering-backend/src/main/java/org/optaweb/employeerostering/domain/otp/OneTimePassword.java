package org.optaweb.employeerostering.domain.otp;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

@Entity
public class OneTimePassword {
    private static final Integer TOKEN_LENGTH = 6;
    private static final Integer TOKEN_MAX = 10; // exclusive
    public static final Integer EXPIRY_MINUTES = 5;
    private static final String OTP_ALGO = "SHA1PRNG";
    private static SecureRandom SR = makeSecureRandom();

    private static SecureRandom makeSecureRandom() {
        try {
            return SecureRandom.getInstance(OTP_ALGO);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not initialise OneTimePassword", e);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String token;
    private Integer retries = 3;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    public OneTimePassword (@Email String email) {
        this.email = email;
        this.token = generateToken();
        this.expiry = calculateExpiry();
    }

    private static String generateToken() {
        StringBuilder sb = new StringBuilder(TOKEN_LENGTH);

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            sb.append(SR.nextInt(TOKEN_MAX));
        }
        return sb.toString();
    }

    private static Date calculateExpiry () {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, OneTimePassword.EXPIRY_MINUTES);
        return new Date(cal.getTime().getTime());
    }

    @Override
    public String toString() {
        return token;
    }
}
