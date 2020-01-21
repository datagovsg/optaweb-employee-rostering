package org.optaweb.employeerostering.domain.otp;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.optaweb.employeerostering.domain.user.User;


public class OneTimePassword {
    private static final Integer TOKEN_LENGTH = 6;
    private static final Integer EXPIRY_MINUTES = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Integer retries = 3;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    @OneToOne(targetEntity = User.class, fetch=FetchType.EAGER)
    private User user;

    public OneTimePassword (User user) throws NoSuchAlgorithmException {
        token = generateToken();
        expiry = calculateExpiry(EXPIRY_MINUTES);
    }

    private static String generateToken() throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder(TOKEN_LENGTH);
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            sb.append(sr.nextInt());
        }

        return sb.toString();
    }

    private static Date calculateExpiry(Integer minutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, minutes);
        return new Date(cal.getTime().getTime());
    }
}
