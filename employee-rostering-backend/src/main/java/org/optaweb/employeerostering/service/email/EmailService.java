package org.optaweb.employeerostering.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.optaweb.employeerostering.domain.otp.OneTimePassword;
import org.optaweb.employeerostering.domain.user.User;
import org.optaweb.employeerostering.exception.OtpMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpMail(User user, OneTimePassword otp) throws OtpMailException {
        String toEmail = user.getEmail();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setFrom("donotreply@mail.roster.gov.sg");
            mimeMessageHelper.setSubject("One-Time Password for Rostering");
            mimeMessageHelper.setText(
                    "Your OTP is <b>" + otp.toString() + "</b>. " +
                            "It will expire in " + OneTimePassword.EXPIRY_MINUTES + " minutes. " +
                            "Please use this to login to your Rostering account.",
                    true);

            javaMailSender.send(mimeMessage);
            logger.info("OTP sent to:\t" + toEmail);

        } catch (MessagingException e) {
            throw new OtpMailException("Could not email OTP to user:\t" + toEmail, e);
        }
    }
}
