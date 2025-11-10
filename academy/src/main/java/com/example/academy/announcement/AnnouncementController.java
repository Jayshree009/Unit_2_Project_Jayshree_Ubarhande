package com.example.academy.announcement;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "http://localhost:5174")
public class AnnouncementController {
    private final AnnouncementRepository repo;
    public AnnouncementController(AnnouncementRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Announcement> all() { return repo.findAll(); }

    @PostMapping
    public Announcement create(@RequestBody Announcement a) { return repo.save(a); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
