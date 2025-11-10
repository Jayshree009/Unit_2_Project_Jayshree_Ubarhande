package com.example.academy.tour;

import com.example.academy.mail.EmailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourRepository repo;
    private final EmailService email;

    public TourController(TourRepository repo, EmailService email) {
        this.repo = repo;
        this.email = email;
    }

    @GetMapping
    public List<Tour> all() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<Tour> create(@RequestBody Tour t) {
        // set default status if null
        if (t.getStatus() == null || t.getStatus().isBlank()) {
            t.setStatus("PENDING");
        }
        Tour saved = repo.save(t);

        // fire-and-forget email (don’t fail booking if email fails)
        try {
            String dateStr = (saved.getPreferredDate() != null) ? saved.getPreferredDate().toString() : "TBD";
            email.sendTourConfirmation(
                    saved.getParentEmail(),
                    saved.getParentName(),
                    saved.getChildName(),
                    dateStr
            );
        } catch (Exception ignore) {}

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
