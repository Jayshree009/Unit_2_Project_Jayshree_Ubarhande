package com.example.academy.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.academy.model.Program;

public interface ProgramRepository extends JpaRepository<Program,Long>{
}
