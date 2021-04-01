package com.sparta.firstproject.service;


import com.sparta.firstproject.model.Board;
import com.sparta.firstproject.repository.BoardRepository;
import com.sparta.firstproject.Dto.BoardRequestDto;
import com.sparta.firstproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        board.update(requestDto);
        return board.getId();
    }
    @Transactional
    public Board writeBoard(BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = new Board(requestDto, userDetails.getUsername());
        boardRepository.save(board);
        return board;
    }
}
