package com.nurul.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private  JavaMailSender mailSender;

    public void sendMail(String toMail, String subject, String body) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nurulcse09@gmail.com");
            message.setTo(toMail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("Mail sent successfully");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
