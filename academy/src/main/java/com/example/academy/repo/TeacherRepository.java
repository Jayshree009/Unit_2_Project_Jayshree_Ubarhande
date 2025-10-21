package com.example.academy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.academy.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}
