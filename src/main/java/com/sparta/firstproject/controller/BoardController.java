package com.sparta.firstproject.controller;

import com.sparta.firstproject.model.Board;
import com.sparta.firstproject.repository.BoardRepository;
import com.sparta.firstproject.dto.BoardRequestDto;
import com.sparta.firstproject.security.UserDetailsImpl;
import com.sparta.firstproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/api/boards")
    public List<Board> readBoard(){
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping("/api/boards")
    public Board createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        model.addAttribute("username", userDetails.getUsername());
        Board board = new Board(requestDto);
        return boardRepository.save(board);
    }

    @GetMapping("/api/boards/{id}")
    public List<Board> detailBoard(@PathVariable Long id){
        return boardRepository.findAllById(id);
    }

    @DeleteMapping("/api/boards/{id}")
    public Long deleteMemo(@PathVariable Long id){
        boardRepository.deleteById(id);
        return id;
    }

    @PutMapping("/api/boards/{id}")
    public Long updateMemo(@PathVariable Long id , @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        String postername = requestDto.getName();
        String loggedinuser= userDetails.getUsername();
        if (!postername.equals(loggedinuser)){
            throw new IllegalArgumentException("wronguser");
        }
        boardService.update(id, requestDto);
        return id;
    }
}