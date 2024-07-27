package com.baconbao.portfolio.controller;

import com.baconbao.portfolio.dto.CommentsDTO;
import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.services.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentsService commentsService;
    @PostMapping("/save")
    public ResponseEntity<CommentsDTO> save(@RequestBody CommentsDTO commentsDTO){
        return ResponseEntity.ok(commentsService.saveComment(commentsDTO));
    }
}
