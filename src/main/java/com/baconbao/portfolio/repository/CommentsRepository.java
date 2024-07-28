package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Comments;
import com.baconbao.portfolio.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    Optional<Comments> findById(Integer integer);
    List<Comments> findByProfile(Profile profile);
    List<Comments> findByUserId(Integer id);
}
