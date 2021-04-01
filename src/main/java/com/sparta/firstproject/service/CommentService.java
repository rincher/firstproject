package com.sparta.firstproject.service;

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

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    @Transactional
    public Comment writeComment(String comments, User user ){
        Comment comment = new Comment(comments, user);
        commentRepository.save(comment);
        return comment;
    }
}
