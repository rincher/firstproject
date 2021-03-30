package com.sparta.firstproject.repository;

import com.sparta.firstproject.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAllByOrderByCreatedAtDesc();
    public List<Board> findAllById(Long id);
}
