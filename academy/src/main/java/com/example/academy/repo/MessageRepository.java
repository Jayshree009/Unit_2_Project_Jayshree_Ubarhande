package com.example.academy.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.academy.model.Message;

public interface MessageRepository extends JpaRepository<Message,Long>{
}
