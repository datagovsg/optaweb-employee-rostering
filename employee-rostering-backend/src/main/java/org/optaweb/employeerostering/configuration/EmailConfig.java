package org.optaweb.employeerostering.configuration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Configuration
@Profile(Profiles.DEFAULT)
public class EmailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        return new PrintScreenMailSender();
    }
}

/**
 * A mail sender that simply prints the contents of the email to the screen on send.
 */
class PrintScreenMailSender extends JavaMailSenderImpl {

    private void printMessage(MimeMessage m) {
        System.out.println(m.toString());
    }

    private void printMessage(SimpleMailMessage m) {
        System.out.println(m.toString());
    }

    @Override
    public void send(MimeMessage m) throws MailException {
        printMessage(m);
    }

    @Override
    public void send(MimeMessage... ms) throws MailException {
        for (MimeMessage m: ms) {
            this.send(m);
        }
    }

    @Override
    public void send(MimeMessagePreparator mp) throws MailException {
        try {
            MimeMessage m = this.createMimeMessage();
            mp.prepare(m);
            this.send(m);
        } catch (MailException e){
            throw e;
        } catch (MessagingException e) {
            throw new MailParseException(e);
        } catch (Exception e) {
            throw new MailPreparationException(e);
        }

    }

    @Override
    public void send(MimeMessagePreparator... mps) throws MailException {
        for (MimeMessagePreparator mp: mps) {
            this.send(mp);
        }
    }

    @Override
    public void send (SimpleMailMessage simpleMailMessage) throws MailException {
        this.printMessage(simpleMailMessage);
    }

    @Override
    public void send (SimpleMailMessage... simpleMailMessages) throws MailException {
        for (SimpleMailMessage m: simpleMailMessages) {
            this.printMessage(m);
        }
    }
}