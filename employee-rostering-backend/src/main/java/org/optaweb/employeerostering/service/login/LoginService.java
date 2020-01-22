package org.optaweb.employeerostering.service.login;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.optaweb.employeerostering.domain.agency.Agency;
import org.optaweb.employeerostering.domain.otp.OneTimePassword;
import org.optaweb.employeerostering.domain.user.User;
import org.optaweb.employeerostering.service.agency.AgencyService;
import org.optaweb.employeerostering.service.otp.OtpService;
import org.optaweb.employeerostering.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    AgencyService agencyService;

    @Autowired
    UserService userService;

    @Autowired
    OtpService otpService;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public void loginOrRegisterNewUser (String email) throws Exception {

        String emailDomain = email.split("@")[1];

        // Check if agency exists
        Optional<Agency> agencyOptional = agencyService.getByEmailDomain(emailDomain);

        if(!agencyOptional.isPresent()) {
            logger.info("Agency not found for email: " + email);
            throw new Exception("Agency not registered");
        }

        // Agency found, create user
        Agency agency = agencyOptional.get();
        User user = userService.getOrCreateUser(agency, email);

        // Generate OTP
        OneTimePassword otp;
        try {
            otp = otpService.createOtp(user.getEmail());
        } catch (NoSuchAlgorithmException e) {
            logger.error("Could not generate OTP for user", e);
            throw e;
        }

        // Send email
        logger.info("Mock: Sending email to: " + user.getEmail());
    }
}
