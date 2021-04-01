package com.sparta.firstproject.service;

import com.sparta.firstproject.Dto.BoardRequestDto;
import com.sparta.firstproject.Dto.CommentRequestDto;
import com.sparta.firstproject.model.Board;
import com.sparta.firstproject.model.Comment;
import com.sparta.firstproject.model.User;
import com.sparta.firstproject.repository.CommentRepository;
import com.sparta.firstproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment writeComment(String comments, User user) {
        Comment comment = new Comment(comments, user);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Long update(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        comment.update(requestDto);
        return comment.getId();
    }
}
