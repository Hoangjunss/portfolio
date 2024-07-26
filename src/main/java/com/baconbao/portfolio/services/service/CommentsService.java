package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.CommentsDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentsService {
    CommentsDTO findById(Integer id);
    CommentsDTO saveComment(CommentsDTO commentsDTO);
    CommentsDTO updateComment(CommentsDTO commentsDTO);
}
