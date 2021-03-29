package com.sparta.firstproject.model;

import com.sparta.firstproject.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity

public class Board extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;


    public Board(String name, String contents, String title){
        this.name = name;
        this.contents = contents;
        this.title = title;
    }

    public Board(BoardRequestDto requestDto){
        this.name = requestDto.getName();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BoardRequestDto requestDto){
        this.name = requestDto.getName();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
