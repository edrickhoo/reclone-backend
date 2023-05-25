package com.example.reddit.service;

import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.model.NotificationEmail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class NewEmailService {

    @Async
    void sendMail(NotificationEmail notificationEmail) throws IOException {

        Dotenv dotenv = Dotenv.load();

        String SENDGRID_API_KEY = dotenv.get("SENDGRID_API_KEY");

        Email from = new Email("edric.khoo@hotmail.com");
        String subject = notificationEmail.getSubject();
        Email to = new Email(notificationEmail.getRecipient());
        Content content = new Content("text/plain", notificationEmail.getBody());
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
