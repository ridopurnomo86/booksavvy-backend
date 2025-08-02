package com.booksavvy.server.controller;

import com.booksavvy.server.dto.common.Response;
import com.booksavvy.server.dto.user.UserResponse;
import com.booksavvy.server.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/profile")
@Validated
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public Response getUserProfile(@AuthenticationPrincipal UserResponse user) {
        UserResponse userProfile = userService.findByUserId(user.getId());

        return new Response("success", "success", "success", userProfile);
    }

}
