package com.sparta.firstproject.model;

import com.sparta.firstproject.Dto.BoardRequestDto;
import com.sparta.firstproject.Dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped{

    public Comment(String comments, User user) {
        this.comment = comments;
        this.user = user;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Comment(CommentRequestDto requestDto, User user) {
        this.comment = requestDto.getComments();
        this.user = user;
    }
    public void update(CommentRequestDto requestDto){
        this.comment = requestDto.getComments();
    }

}
