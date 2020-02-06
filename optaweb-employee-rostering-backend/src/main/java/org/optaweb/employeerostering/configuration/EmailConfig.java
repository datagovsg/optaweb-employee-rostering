package org.optaweb.employeerostering.configuration;

import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;


@Configuration
public class EmailConfig {

    @Bean
    @Profile({ Profiles.TEST, Profiles.DEVELOPMENT, Profiles.STAGING })
    public JavaMailSender getMailSender() {
        return new PrintScreenMailSender();
    }
}

/**
 * A mail sender that simply prints the contents of the email to the screen on send.
 */
class PrintScreenMailSender implements JavaMailSender {
    private static final Logger logger = LoggerFactory.getLogger(PrintScreenMailSender.class);

    private void print (MimeMessage m) {
        MimeMessageParser p = new MimeMessageParser(m);

        try {
            String sb = "From: " + p.getFrom() + "\n" +
                "To: " + p.getTo() + "\n" +
                "cc: " + p.getCc() + "\n" +
                "bcc: " + p.getBcc() + "\n" +
                "Subject: " + p.getSubject() + "\n" +
                "Attachments: " + p.getAttachmentList() + "\n" +
                p.parse().getHtmlContent();
            logger.info(sb);
        } catch (Exception e) {
            logger.error("Could not print email to screen:\t", e);
        }
    }

    private void print (SimpleMailMessage m) {
        System.out.println(m.toString());
    }

    @Override
    public MimeMessage createMimeMessage() {
        return new MimeMessage((Session) null);
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailParseException {
        try {
            return new MimeMessage(null, contentStream);
        }
        catch (MessagingException ex) {
            throw new MailParseException("Could not parse raw MIME content", ex);
        }
    }

    @Override
    public void send(MimeMessage m) throws MailException {
        print(m);
    }

    @Override
    public void send(MimeMessage... ms) throws MailException {
        for (MimeMessage m : ms) {
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
        for (MimeMessagePreparator mp : mps) {
            this.send(mp);
        }
    }

    @Override
    public void send (SimpleMailMessage simpleMailMessage) throws MailException {
        this.print(simpleMailMessage);
    }

    @Override
    public void send (SimpleMailMessage... simpleMailMessages) throws MailException {
        for (SimpleMailMessage m : simpleMailMessages) {
            this.print(m);
        }
    }
}