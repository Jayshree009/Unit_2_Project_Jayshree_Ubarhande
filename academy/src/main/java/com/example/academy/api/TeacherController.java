package com.example.academy.api;

import com.example.academy.model.Teacher;
import com.example.academy.repo.TeacherRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:5173") // adjust for your FE
public class TeacherController {

    private final TeacherRepository repo;
    public TeacherController(TeacherRepository repo) { this.repo = repo; }

    // DTOs (nested for MVP)
    public static class CreateTeacher {
        //@NotBlank @Size(max = 50) public String full_name;
        @NotBlank @Size(max = 50) public String full_name;
        @NotBlank @Email           public String email;
        @Size(max = 80)            public String bio;
        public Boolean active; // optional; default true
    }
    public static class UpdateTeacher {
        //public String firstName;
        public String full_name;
        @Email public String email;
        public String bio;
        public Boolean active;
    }
    public static class TeacherResponse {
        public Long id;

        public String lastName;
        public String email;
        public String bio;
        public boolean active;

        public TeacherResponse(Long id, String fn, String ln, String email) {
            this.id = id;  this.lastName = ln;
            this.email = email; this.bio = bio; this.active = active;
        }
    }

    // Endpoints
    @GetMapping
    public List<TeacherResponse> all() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> one(@PathVariable Long id) {
        return repo.findById(id)
                .map(t -> ResponseEntity.ok(toResponse(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> create(@Valid @RequestBody CreateTeacher in) {
        Teacher t = fromCreate(in);
        Teacher saved = repo.save(t);
        return ResponseEntity
                .created(URI.create("/api/teachers/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> update(@PathVariable Long id,
                                                  @RequestBody UpdateTeacher in) {
        return repo.findById(id).map(existing -> {
            merge(existing, in);
            Teacher saved = repo.save(existing);
            return ResponseEntity.ok(toResponse(saved));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Mappers
    private Teacher fromCreate(CreateTeacher in) {
        Teacher t = new Teacher();

        t.setFullName(in.full_name);
        t.setEmail(in.email);
        t.setBio(in.bio);
     //   t.setActive(in.active == null ? true : in.active);
        return t;
    }

    private void merge(Teacher t, UpdateTeacher in) {
        if (in.full_name != null)  t.setFullName(in.full_name);
        if (in.email != null)     t.setEmail(in.email);
        if (in.bio != null)   t.setBio(in.bio);
        //if (in.active != null)    t.setActive(in.active);
    }

    private TeacherResponse toResponse(Teacher t) {
        return new TeacherResponse(
                t.getId(), t.getFullName(),
                t.getEmail(), t.getBio()
        );
    }
}
