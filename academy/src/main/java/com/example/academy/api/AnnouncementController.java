package com.example.academy.api;

import com.example.academy.model.Announcement;
import com.example.academy.repo.AnnouncementRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "http://localhost:5173") // might need to adjust for React dev server
public class AnnouncementController {

    private final AnnouncementRepository repo;

    public AnnouncementController(AnnouncementRepository repo) {
        this.repo = repo;
    }


     //DTOs (nested, MVP style)

    // Create (request) DTO
    public static class CreateAnnouncement {
        @NotBlank @Size(max = 200)
        public String title;

        @NotBlank
        public String message;


    }

    // Update (request) DTO – all optional fields for PATCH-like PUT
    public static class UpdateAnnouncement {
        public String title;
        public String message;
       // public String audience;
        //public Boolean published;
    }

    // Response DTO – what the client sees back
    public static class AnnouncementResponse {
        public Long id;
        public String title;
        public String message;
        //public String audience;
        //public boolean published;
        public LocalDateTime createdAt;

        public AnnouncementResponse(Long id, String title, String message) {
            this.id = id; this.title = title; this.message = message;
          this.createdAt = createdAt;
        }
    }


     //Endpoints

    @GetMapping
    public List<AnnouncementResponse> all() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> one(@PathVariable Long id) {
        return repo.findById(id)
                .map(a -> ResponseEntity.ok(toResponse(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnnouncementResponse> create(@Valid @RequestBody CreateAnnouncement in) {
        Announcement entity = fromCreate(in);
        Announcement saved = repo.save(entity);
        return ResponseEntity
                .created(URI.create("/api/announcements/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> update(@PathVariable Long id,
                                                       @RequestBody UpdateAnnouncement in) {
        return repo.findById(id).map(existing -> {
            merge(existing, in);
            Announcement saved = repo.save(existing);
            return ResponseEntity.ok(toResponse(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

     //Mapping helpers

    private Announcement fromCreate(CreateAnnouncement in) {
        Announcement a = new Announcement();
        a.setTitle(in.title);
        a.setMessage(in.message);

        return a;
    }

    private void merge(Announcement a, UpdateAnnouncement in) {
        if (in.title != null) a.setTitle(in.title);
        if (in.message != null) a.setMessage(in.message);

    }

    private AnnouncementResponse toResponse(Announcement a) {
        return new AnnouncementResponse(
                a.getId(), a.getTitle(), a.getMessage()
               // a.getCreatedAt()
        );
    }
}
