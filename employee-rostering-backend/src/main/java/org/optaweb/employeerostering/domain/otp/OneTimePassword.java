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

import org.optaweb.employeerostering.exception.OtpCreationException;

@Entity
public class OneTimePassword {
    private static final Integer TOKEN_LENGTH = 6;
    private static final Integer TOKEN_MAX = 10; // exclusive
    private static final Integer EXPIRY_MINUTES = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String token;
    private Integer retries = 3;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    public OneTimePassword (@Email String email) throws OtpCreationException {
        this.email = email;
        this.token = generateToken();
        this.expiry = calculateExpiry();
    }

    private static String generateToken() throws OtpCreationException {
        try {
            StringBuilder sb = new StringBuilder(TOKEN_LENGTH);
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

            for (int i = 0; i < TOKEN_LENGTH; i++) {
                sb.append(sr.nextInt(TOKEN_MAX));
            }
            System.out.println("Generating token " + sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new OtpCreationException("OneTimePassword could not be created.", e);
        }

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
