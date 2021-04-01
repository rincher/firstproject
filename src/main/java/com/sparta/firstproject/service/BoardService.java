package com.sparta.firstproject.service;


import com.sparta.firstproject.model.Board;
import com.sparta.firstproject.model.Comment;
import com.sparta.firstproject.model.User;
import com.sparta.firstproject.repository.BoardRepository;
import com.sparta.firstproject.Dto.BoardRequestDto;
import com.sparta.firstproject.repository.CommentRepository;
import com.sparta.firstproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

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
        Board board = new Board(requestDto, userDetails.getUser().getId());
        boardRepository.save(board);
        return board;
    }
    @Transactional
    public Board addComment(Long boardId, Long commentId, User user){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("해당 게시물이 존재하지 않습니다"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("해당 댓글을 찾지 못하였습니다."));

        Long userId = user.getId();
        Long boardUserId = board.getUserId();
        Long commentUserId = comment.getUser().getId();

        board.addComment(comment);
        return board;
    }
}
