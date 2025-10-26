package com.example.academy.api;

import com.example.academy.model.Review;
import com.example.academy.repo.ReviewRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewRepository repo;
    public ReviewController(ReviewRepository repo) { this.repo = repo; }

    /* DTOs */
    public static class CreateReview {
        @NotBlank @Size(max = 80)  public String parentName;
        @Min(1) @Max(5)            public int rating;
        @Size(max = 1000)          public String comment;
    }
    public static class UpdateReview {
        public String parentName;
        @Min(1) @Max(5) public Integer rating;
        public String comment;
    }
    public static class ReviewResponse {
        public Long id;
        public String parentName;
        public int rating;
        public String comment;
        public LocalDateTime createdAt;

        public ReviewResponse(Long id, String parentName, int rating, String comment, LocalDateTime createdAt) {
            this.id = id; this.parentName = parentName; this.rating = rating; this.comment = comment; this.createdAt = createdAt;
        }
    }

    /* Endpoints */
    @GetMapping
    public List<ReviewResponse> all() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> one(@PathVariable Long id) {
        return repo.findById(id).map(r -> ResponseEntity.ok(toResponse(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@Valid @RequestBody CreateReview in) {
        Review r = fromCreate(in);
        if (r.getCreatedAt() == null) r.setCreatedAt(LocalDateTime.now());
        Review saved = repo.save(r);
        return ResponseEntity.created(URI.create("/api/reviews/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(@PathVariable Long id, @RequestBody UpdateReview in) {
        return repo.findById(id).map(existing -> {
            merge(existing, in);
            Review saved = repo.save(existing);
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
    private Review fromCreate(CreateReview in) {
        Review r = new Review();
        r.setParentName(in.parentName);
        r.setRating(in.rating);
        r.setComment(in.comment);
        return r;
    }

    private void merge(Review r, UpdateReview in) {
        if (in.parentName != null) r.setParentName(in.parentName);
        if (in.rating != null)     r.setRating(in.rating);
        if (in.comment != null)    r.setComment(in.comment);
    }

    private ReviewResponse toResponse(Review r) {
        return new ReviewResponse(r.getId(), r.getParentName(), r.getRating(), r.getComment(), r.getCreatedAt());
    }
}
