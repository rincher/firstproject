package com.sparta.firstproject.controller;

import com.sparta.firstproject.model.Board;
import com.sparta.firstproject.repository.BoardRepository;
import com.sparta.firstproject.Dto.BoardRequestDto;
import com.sparta.firstproject.security.UserDetailsImpl;
import com.sparta.firstproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/api/boards")
    public List<Board> readBoard() {
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping("/api/boards")
    public Board createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        Board board = boardService.writeBoard(requestDto, userDetails);
        return board;
    }

    @GetMapping("/api/boards/{id}")
    public List<Board> detailBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        return boardRepository.findAllById(id);
    }

    @DeleteMapping("/api/boards/{id}")
    public Long deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       Optional<Board> getBoard = boardRepository.findById(id);
       Long loggedinuser = userDetails.getUser().getId();
       Long posteruser = getBoard.get().getUserId();
       if (loggedinuser == posteruser){
           boardRepository.deleteById(id);
           return id;}
       throw new IllegalArgumentException("다른 사용자 댓글을 삭제 하실 수 없습니다");
    }

    @PutMapping("/api/boards/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        String postername = requestDto.getUsername();
        String loggedinuser = userDetails.getUsername();
        if (!postername.equals(loggedinuser)) {
            throw new IllegalArgumentException("wronguser");
        }
        boardService.update(id, requestDto);
        return id;
    }
}