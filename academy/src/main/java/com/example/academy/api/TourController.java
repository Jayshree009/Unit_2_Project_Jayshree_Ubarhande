package com.example.academy.api;

import com.example.academy.model.Tour;
import com.example.academy.repo.TourRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
@CrossOrigin(origins = "http://localhost:5173")
public class TourController {

    private final TourRepository repo;
    public TourController(TourRepository repo) { this.repo = repo; }

    /* DTOs */
    public static class CreateTour {
        @NotBlank @Size(max = 80) public String parentName;
        @NotBlank @Email          public String email;
        @Size(max = 30)           public String phone;
        @NotNull                  public LocalDate preferredDate;
       // @Size(max = 500)          public String message;
        @Size(max = 20)           public String status; // e.g., "NEW","CONFIRMED","DONE"
    }
    public static class UpdateTour {
        public String parentName;
        @Email public String email;
        public String phone;
        public LocalDate preferredDate;
        //public String message;
        public String status;
    }
    public static class TourResponse {
        public Long id;
        public String parentName;
        public String email;
        public String phone;
        public LocalDate preferredDate;
        //public String message;
        public String status;

        public TourResponse(Long id, String parentName, String email, String phone,
                            LocalDate preferredDate, String status) {
            this.id = id; this.parentName = parentName; this.email = email; this.phone = phone;
            this.preferredDate = preferredDate; this.status = status;
        }
    }

    /* Endpoints */
    @GetMapping
    public List<TourResponse> all() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> one(@PathVariable Long id) {
        return repo.findById(id).map(t -> ResponseEntity.ok(toResponse(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TourResponse> create(@Valid @RequestBody CreateTour in) {
        Tour saved = repo.save(fromCreate(in));
        return ResponseEntity.created(URI.create("/api/tours/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourResponse> update(@PathVariable Long id, @RequestBody UpdateTour in) {
        return repo.findById(id).map(existing -> {
            merge(existing, in);
            Tour saved = repo.save(existing);
            return ResponseEntity.ok(toResponse(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /* Mappers */
    private Tour fromCreate(CreateTour in) {
        Tour t = new Tour();
        t.setParentName(in.parentName);
        t.setEmail(in.email);
        t.setPhone(in.phone);
        t.setPreferredDate(in.preferredDate);
        //t.setMessage(in.message);
        t.setStatus(in.status == null ? "NEW" : in.status);
        return t;
    }

    private void merge(Tour t, UpdateTour in) {
        if (in.parentName != null)   t.setParentName(in.parentName);
        if (in.email != null)        t.setEmail(in.email);
        if (in.phone != null)        t.setPhone(in.phone);
        if (in.preferredDate != null)t.setPreferredDate(in.preferredDate);
        //if (in.message != null)      t.setMessage(in.message);
        if (in.status != null)       t.setStatus(in.status);
    }

    private TourResponse toResponse(Tour t) {
        return new TourResponse(t.getId(), t.getParentName(), t.getEmail(), t.getPhone(),
                t.getPreferredDate(), t.getStatus());
    }
}
