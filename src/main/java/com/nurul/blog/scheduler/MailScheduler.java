package com.nurul.blog.scheduler;


import com.nurul.blog.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailScheduler {

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 9 * * *")
    public  void sendCronEmail()
    {
        mailService.sendMail(
                "nurulcse09@gmail.com",
                "Good Morning Greet",
                "Good morning! wishing a great day."
        );
    }
}
