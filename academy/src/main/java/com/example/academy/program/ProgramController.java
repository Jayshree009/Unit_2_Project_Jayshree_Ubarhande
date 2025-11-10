package com.example.academy.program;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
@CrossOrigin(origins = "http://localhost:5174")
public class ProgramController {
    private final ProgramRepository repo;
    public ProgramController(ProgramRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Program> all() { return repo.findAll(); }

    @PostMapping
    public Program create(@RequestBody Program p) { return repo.save(p); }

    @GetMapping("/{id}")
    public Program get(@PathVariable Long id) { return repo.findById(id).orElse(null); }
}
