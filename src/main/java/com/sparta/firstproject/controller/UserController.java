package com.sparta.firstproject.controller;

import com.sparta.firstproject.dto.SignupRequestDto;
import com.sparta.firstproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/user/forbidden")
    public String forbidden(){ return  "forbidden";}

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code){
        userService.kakaoLogin(code);

        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String login(){ return "login";}

    @GetMapping("/user/login/error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/user/signup")
    public String signup(){return "signup";}

    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto, Model model){
        try {
            userService.registerUser(requestDto);
        }catch (IllegalArgumentException e){
            model.addAttribute("message", e.getMessage());
            return "signup";
        }
        return "redirect:/user/login";
    }
}
