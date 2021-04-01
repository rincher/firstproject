package com.sparta.firstproject.repository;

import com.sparta.firstproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByOrderByCreatedAtAsc();
}
