package com.example.academy.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.academy.model.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
