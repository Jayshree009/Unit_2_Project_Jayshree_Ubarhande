package com.example.academy.api;

import com.example.academy.model.User;
import com.example.academy.repo.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository repo;
    public UserController(UserRepository repo) { this.repo = repo; }

    /* DTOs */
    public static class CreateUser {
        @NotBlank @Size(max = 80) public String fullName;
        @NotBlank @Email          public String email;
        @NotBlank @Size(max = 20) public String role; // "ADMIN","PARENT","STAFF"
        @Size(max = 120)          public String passwordHash; // placeholder for MVP
    }
    public static class UpdateUser {
        public String fullName;
        @Email public String email;
        public String role;
        public String passwordHash;
    }
    public static class UserResponse {
        public Long id;
        public String fullName;
        public String email;
        public String role;

        public UserResponse(Long id, String fullName, String email, String role) {
            this.id = id; this.fullName = fullName; this.email = email; this.role = role;
        }
    }

    /* Endpoints */
    @GetMapping
    public List<UserResponse> all() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> one(@PathVariable Long id) {
        return repo.findById(id).map(u -> ResponseEntity.ok(toResponse(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUser in) {
        User saved = repo.save(fromCreate(in));
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UpdateUser in) {
        return repo.findById(id).map(existing -> {
            merge(existing, in);
            User saved = repo.save(existing);
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
    private User fromCreate(CreateUser in) {
        User u = new User();
        u.setFullName(in.fullName);
        u.setEmail(in.email);
        u.setRole(in.role);
        u.setPasswordHash(in.passwordHash);
        return u;
    }

    private void merge(User u, UpdateUser in) {
        if (in.fullName != null)     u.setFullName(in.fullName);
        if (in.email != null)        u.setEmail(in.email);
        if (in.role != null)         u.setRole(in.role);
        if (in.passwordHash != null) u.setPasswordHash(in.passwordHash);
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getFullName(), u.getEmail(), u.getRole());
    }
}
