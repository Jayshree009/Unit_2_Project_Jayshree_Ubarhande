package com.example.academy.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dev/mail")
public class DevMailController {

    private final EmailService emailService;

    public DevMailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Quick manual test:
    // GET http://localhost:8081/api/dev/mail/test?to=you@example.com&parent=Rita&child=Arjun&date=2025-11-15
    @GetMapping("/test")
    public ResponseEntity<String> testSend(
            @RequestParam("to") String toEmail,
            @RequestParam(value = "parent", defaultValue = "Parent") String parentName,
            @RequestParam(value = "child",  defaultValue = "Student") String childName,
            @RequestParam(value = "date",   defaultValue = "TBD") String dateText
    ) {
        try {
            emailService.sendTourConfirmation(toEmail, parentName, childName, dateText);
            return ResponseEntity.ok("Email sent to " + toEmail);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Send failed: " + ex.getMessage());
        }
    }
}
