package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.CommentsDTO;
import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.exception.CustomException;
import com.baconbao.portfolio.exception.Error;
import com.baconbao.portfolio.model.Comments;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.repository.CommentsRepository;
import com.baconbao.portfolio.services.service.CommentsService;
import com.baconbao.portfolio.services.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProfileService profileService;

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    private Comments convertComments(CommentsDTO commentsDTO){ return modelMapper.map(commentsDTO, Comments.class);}

    private CommentsDTO convertCommentsDTO(Comments comments){ return modelMapper.map(comments, CommentsDTO.class); }

    private Comments save(CommentsDTO commentsDTO){
        Profile profile=profileService.convertToModel(profileService.findById(commentsDTO.getIdProfile()));
        Comments comments = Comments.builder()
                .id(getGenerationId())
                .content(commentsDTO.getContent())
                .createAt(commentsDTO.getCreateAt())
                .profile(profile)
                .build();
        return commentsRepository.save(comments);
    }

    @Override
    public CommentsDTO findById(Integer id) {
        log.info("Find Comment by id: {}", id);
        return convertCommentsDTO(commentsRepository.findById(id)
                .orElseThrow(()->new CustomException(Error.COMMENT_NOT_FOUND)));
    }

    @Override
    public CommentsDTO saveComment(CommentsDTO commentsDTO) {
        try {
            log.info("Save Comment");
            Comments commments = save(commentsDTO);
            return convertCommentsDTO(commments);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.COMMENT_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public CommentsDTO updateComment(CommentsDTO commentsDTO) {
        try {
            log.info("Update Comment");
            return convertCommentsDTO(commentsRepository.save(convertComments(commentsDTO)));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.COMMENT_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public List<CommentsDTO> getCommentByProfile(Integer id) {
        try {
            log.info("Get Comment by Profile id: {}", id);
            Profile profile=profileService.convertToModel(profileService.findById(id));
            return convertListCommentDTO(commentsRepository.findByProfile(profile));
        } catch (InvalidDataAccessResourceUsageException e){
            log.error("Get Comment by Profile id failed: {}", e.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }

    @Override
    public List<CommentsDTO> getCommentByUser(Integer idUser) {
        try {
            log.info("Get comments by user id: {}", idUser);
            return convertListCommentDTO(commentsRepository.findByUserId(idUser));
        } catch (InvalidDataAccessResourceUsageException e){
            log.error("Get Comment by User id failed: {}", e.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }

    public List<CommentsDTO> convertListCommentDTO(List<Comments> comments){
        return comments.stream()
                .map(comment->convertCommentsDTO(comment))
                .collect(Collectors.toList());
    }
}
