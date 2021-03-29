package com.sparta.firstproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class BoardRequestDto {
    private final String name;
    private final String title;
    private final String contents;
}
