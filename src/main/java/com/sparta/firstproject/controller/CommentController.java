package com.sparta.firstproject.controller;

import com.sparta.firstproject.Dto.CommentRequestDto;
import com.sparta.firstproject.model.Comment;
import com.sparta.firstproject.repository.CommentRepository;
import com.sparta.firstproject.security.UserDetailsImpl;
import com.sparta.firstproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/api/comment")
    public Comment writeComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.writeComment(requestDto.getComments(), userDetails.getUser());
    }

    @GetMapping("/api/comment")
    public List<Comment> readComment(){return commentRepository.findAllByOrderByCreatedAtAsc();}

    @PutMapping("/api/comment/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String postername = requestDto.getUsername();
        String loggedinuser = userDetails.getUsername();
        if (!postername.equals(loggedinuser)) {
            throw new IllegalArgumentException("wronguser");
        }
        commentService.update(id, requestDto);
        return id;
    }
    @DeleteMapping("/api/comment/{id}")
    public Long deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Comment> getComment = commentRepository.findById(id);
        Long posteruser = getComment.get().getUser().getId();
        Long loggedinuser = userDetails.getUser().getId();
        if (posteruser.equals(loggedinuser)) {
            commentRepository.deleteById(id);
            return id;
        }
        throw new IllegalArgumentException("다른 사용자 댓글을 삭제 하실 수 없습니다");
    }
}
