package com.sparta.firstproject.model;

import com.sparta.firstproject.Dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity

public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToMany
    private List<Comment> commentList;

    public Board(BoardRequestDto requestDto, Long userId){
        this.userId = userId;
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BoardRequestDto requestDto){
        this.contents = requestDto.getContents();
    }
    public void addComment(Comment comment){
        this.commentList.add(comment);
    }
}
