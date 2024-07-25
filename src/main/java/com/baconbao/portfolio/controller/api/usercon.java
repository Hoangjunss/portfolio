package com.baconbao.portfolio.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class usercon {
    @GetMapping("/user")
    public ResponseEntity<?> user(){
        return ResponseEntity.ok("ok");
    }

}
