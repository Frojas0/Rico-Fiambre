package com.ricofiambre.ecomerce.servicios;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendConfirmationEmail(String to) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Email Confirmation");
            helper.setText("Please confirm your email using the following link:");

            // Adjuntar el archivo PDF en memoria al correo
//            helper.addAttachment("Ticket.pdf", new ByteArrayResource(pdfOutputStream.toByteArray()));

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Manejar el error de envío del correo
            e.printStackTrace();
        }
    }
}
