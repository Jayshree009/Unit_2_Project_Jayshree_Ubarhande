package com.example.academy.mail;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String apiKey;
    private final Email from;

    public EmailService(
            @Value("${sendgrid.api.key:}") String apiKey,
            @Value("${app.mail.from:}") String fromAddress
    ) {
        this.apiKey = apiKey;
        this.from = (fromAddress == null || fromAddress.isBlank())
                ? new Email("no-reply@example.com", "Chesterfield Academy")
                : EmailBuilder.fromString(fromAddress);
    }

    public boolean sendTourConfirmation(String toEmail, String parentName, String childName, String preferredDate) {
        if (apiKey == null || apiKey.isBlank()) return false;
        try {
            Email to = new Email(toEmail);
            String subject = "Tour Confirmation – Chesterfield Academy";
            String body = String.format(
                    "Hello %s,\n\nThanks for booking a tour for %s.\nPreferred date: %s.\n\nWe look forward to seeing you!\n– Chesterfield Academy",
                    parentName, childName, preferredDate
            );

            Content content = new Content("text/plain", body);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            return response.getStatusCode() >= 200 && response.getStatusCode() < 300;
        } catch (Exception ex) {
            return false;
        }
    }

    // small helper to support "Name <email@x>" in app.mail.from
    static class EmailBuilder {
        static Email fromString(String s) {
            // If value looks like `"Display Name" <addr@example.com>`
            if (s.contains("<") && s.contains(">")) {
                String name = s.substring(0, s.indexOf('<')).replace("\"","").trim();
                String addr = s.substring(s.indexOf('<')+1, s.indexOf('>')).trim();
                return new Email(addr, name.isBlank() ? null : name);
            }
            return new Email(s);
        }
    }
}
