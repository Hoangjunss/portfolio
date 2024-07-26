package com.baconbao.portfolio.controller;

import com.baconbao.portfolio.dto.AuthenticationRequest;
import com.baconbao.portfolio.dto.AuthenticationResponse;
import com.baconbao.portfolio.dto.UserDTO;
import com.baconbao.portfolio.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public ResponseEntity<UserDTO> signIn(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
