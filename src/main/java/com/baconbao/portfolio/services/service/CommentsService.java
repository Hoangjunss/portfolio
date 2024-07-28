package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.CommentsDTO;
import com.baconbao.portfolio.dto.ProfileDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentsService {
    CommentsDTO findById(Integer id);
    CommentsDTO saveComment(CommentsDTO commentsDTO);
    CommentsDTO updateComment(CommentsDTO commentsDTO);
    List<CommentsDTO> getCommentByProfile(Integer id);
    //getcommentbyuser
    List<CommentsDTO> getCommentByUser(Integer idUser);
}
