package com.example.academy.health;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @GetMapping("/ping")
    public String ping() { return "ok"; }
}
