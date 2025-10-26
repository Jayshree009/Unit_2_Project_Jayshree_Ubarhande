package com.example.academy.api;

import com.example.academy.model.Program;
import com.example.academy.repo.ProgramRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
@CrossOrigin(origins = "http://localhost:5173")
public class ProgramController {

    private final ProgramRepository repo;
    public ProgramController(ProgramRepository repo) { this.repo = repo; }

    /* DTOs */
    public static class CreateProgram {
        @NotBlank @Size(max = 80) public String name;
        @Size(max = 60)          public String ageGroup;   // e.g., "Infant", "Toddler", "Pre-K"
        @Size(max = 1000)        public String description;
        //public Double tuition; // monthly tuition, optional
    }
    public static class UpdateProgram {
        public String name;
        public String ageGroup;
        public String description;
        //public Double tuition;
    }
    public static class ProgramResponse {
        public Long id;
        public String name;
        public String ageGroup;
        public String description;
        //public Double tuition;

        public ProgramResponse(Long id, String name, String ageGroup, String description) {
            this.id = id; this.name = name; this.ageGroup = ageGroup; this.description = description;
        }
    }

    /* Endpoints */
    @GetMapping
    public List<ProgramResponse> all() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponse> one(@PathVariable Long id) {
        return repo.findById(id).map(p -> ResponseEntity.ok(toResponse(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProgramResponse> create(@Valid @RequestBody CreateProgram in) {
        Program saved = repo.save(fromCreate(in));
        return ResponseEntity.created(URI.create("/api/programs/" + saved.getId()))
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramResponse> update(@PathVariable Long id, @RequestBody UpdateProgram in) {
        return repo.findById(id).map(existing -> {
            merge(existing, in);
            Program saved = repo.save(existing);
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
    private Program fromCreate(CreateProgram in) {
        Program p = new Program();
        p.setName(in.name);
        p.setAgeGroup(in.ageGroup);
        p.setDescription(in.description);
      //  p.setTuition(in.tuition);
        return p;
    }

    private void merge(Program p, UpdateProgram in) {
        if (in.name != null)        p.setName(in.name);
        if (in.ageGroup != null)    p.setAgeGroup(in.ageGroup);
        if (in.description != null) p.setDescription(in.description);
        //if (in.tuition != null)     p.setTuition(in.tuition);
    }

    private ProgramResponse toResponse(Program p) {
        return new ProgramResponse(p.getId(), p.getName(), p.getAgeGroup(), p.getDescription() /*p.getTuition()*/);
    }
}
