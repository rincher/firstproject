package com.sparta.firstproject.controller;

import com.sparta.firstproject.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    @GetMapping("/board/write")
    public String boardwrite(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("username", userDetails.getUsername());
        return "/boardwrite";
    }
    @GetMapping("/board/{id}" )
    public String detailboard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        model.addAttribute("id", id);
        return "/boarddetail";
    }
}
