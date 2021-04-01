package com.sparta.firstproject.controller;

import com.sparta.firstproject.Dto.CommentRequestDto;
import com.sparta.firstproject.model.Comment;
import com.sparta.firstproject.repository.CommentRepository;
import com.sparta.firstproject.security.UserDetailsImpl;
import com.sparta.firstproject.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/api/comment")
    public Comment writeComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(requestDto.getComments());
        System.out.println(userDetails.getUser());
        Comment comments = new Comment(requestDto, userDetails.getUser());
        return commentRepository.save(comments);
    }

    @GetMapping("/api/comment")
    public List<Comment> readComment(){return commentRepository.findAllByOrderByCreatedAtAsc();}


}
