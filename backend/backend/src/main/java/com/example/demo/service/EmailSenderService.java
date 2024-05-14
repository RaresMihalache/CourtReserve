package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Async
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    public void subscriptionEmail(String toEmail,
                              ByteArrayOutputStream outputStream,
                              String subject) {

        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Subscription receipt");
            byte[] bytes = outputStream.toByteArray();

            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("SubscriptionReceipt.pdf");

            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(pdfBodyPart);
            message.setContent(mimeMultipart);

            message.setSender(new InternetAddress("durateodora11@gmail.com"));
            message.setSubject(subject);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setContent(mimeMultipart);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(message);
    }

    public void sendNotification(String toEmail,
                                 String body,
                                 String subject) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        String html;

        try {
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("message", body);

            helper.setTo(toEmail);
            helper.setSubject(subject);

            html = templateEngine.process("email.html", context);
            helper.setText(html, true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(message);
    }

}