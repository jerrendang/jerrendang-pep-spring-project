package com.example.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    List<Message> findByPostedBy(Integer posted_by);
}
