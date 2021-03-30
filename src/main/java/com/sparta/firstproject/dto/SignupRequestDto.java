package com.sparta.firstproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String password_checker;
    private boolean admin = false;
    private String adminToken = "";
}