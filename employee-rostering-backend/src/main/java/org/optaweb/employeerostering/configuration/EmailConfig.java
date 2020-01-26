package org.optaweb.employeerostering.configuration;

import javax.mail.MessagingException;
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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Configuration
public class EmailConfig {

    @Bean
    @Profile(Profiles.NOT_PRODUCTION)
    public JavaMailSender getDevelopmentMailSender() {
        return new PrintScreenMailSender();
    }
}

/**
 * A mail sender that simply prints the contents of the email to the screen on send.
 */
class PrintScreenMailSender extends JavaMailSenderImpl {
    private static final Logger logger = LoggerFactory.getLogger(PrintScreenMailSender.class);

    private void print (MimeMessage m) {
        MimeMessageParser p = new MimeMessageParser(m);

        try {
            StringBuilder sb = new StringBuilder();

            sb.append("From: ").append(p.getFrom()).append("\n")
                    .append("To: ").append(p.getTo()).append("\n")
                    .append("cc: ").append(p.getCc()).append("\n")
                    .append("bcc: ").append(p.getBcc()).append("\n")
                    .append("Subject: ").append(p.getSubject()).append("\n")
                    .append("Attachments: ").append(p.getAttachmentList()).append("\n")
                    .append(p.parse().getHtmlContent());

            System.out.println(sb.toString());
        } catch (Exception e) {
            logger.error("Could not print email to screen:\t", e);
        }
    }

    private void print (SimpleMailMessage m) {
        System.out.println(m.toString());
    }

    @Override
    public void send(MimeMessage m) throws MailException {
        print(m);
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
        this.print(simpleMailMessage);
    }

    @Override
    public void send (SimpleMailMessage... simpleMailMessages) throws MailException {
        for (SimpleMailMessage m: simpleMailMessages) {
            this.print(m);
        }
    }
}