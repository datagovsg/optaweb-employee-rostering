package org.optaweb.employeerostering.service.login;

import java.util.Optional;

import org.optaweb.employeerostering.domain.agency.Agency;
import org.optaweb.employeerostering.domain.otp.OneTimePassword;
import org.optaweb.employeerostering.domain.user.User;
import org.optaweb.employeerostering.exception.EmailServiceException;
import org.optaweb.employeerostering.service.agency.AgencyService;
import org.optaweb.employeerostering.service.email.EmailService;
import org.optaweb.employeerostering.service.otp.OtpService;
import org.optaweb.employeerostering.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    AgencyService agencyService;
    @Autowired
    UserService userService;
    @Autowired
    OtpService otpService;
    @Autowired
    EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public void loginOrRegisterNewUser (String email)
            throws AuthenticationServiceException {

        String emailDomain = email.split("@")[1];

        // Check if agency exists
        Optional<Agency> agencyOptional = agencyService.getByEmailDomain(emailDomain);

        if(!agencyOptional.isPresent()) {
            throw new AuthenticationServiceException("Agency is not registered for " + email);
        }

        // Agency found, create user
        Agency agency = agencyOptional.get();
        User user = userService.getOrCreateUser(agency, email);

        // Generate OTP
        OneTimePassword otp = otpService.createOtp(user.getEmail());

        // Send OTP email
        try {
            emailService.sendOtpMail(user, otp);
        } catch (EmailServiceException e) {
            throw new AuthenticationServiceException("Could not send OTP to user:\t" + email, e);
        }
    }
}
