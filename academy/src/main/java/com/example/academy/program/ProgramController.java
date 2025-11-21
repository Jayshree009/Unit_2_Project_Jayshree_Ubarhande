package com.example.academy.program;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
@CrossOrigin(origins = "http://localhost:5174")
public class ProgramController {

    private final ProgramRepository repo;

    public ProgramController(ProgramRepository repo) {
        this.repo = repo;
    }

    // ---- READ: get all programs ----
    // GET /api/programs
    @GetMapping
    public List<Program> all() {
        return repo.findAll();
    }

    // ---- CREATE: add a new program ----
    // POST /api/programs
    @PostMapping
    public Program create(@RequestBody Program p) {
        return repo.save(p);
    }

    // ---- READ: get single program by id ----
    // GET /api/programs/{id}
    @GetMapping("/{id}")
    public Program get(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    // ---- UPDATE: replace an existing program ----
    // PUT /api/programs/{id}
    @PutMapping("/{id}")
    public Program update(@PathVariable Long id, @RequestBody Program updated) {
        // Simple replace: trust the body and set the id from path
        updated.setId(id);
        return repo.save(updated);
    }

    // ---- DELETE: remove a program ----
    // DELETE /api/programs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            // 404 if not found (optional but nice)
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);

        // Return 204 No Content – matches frontend http() helper
        return ResponseEntity.noContent().build();
    }
}
