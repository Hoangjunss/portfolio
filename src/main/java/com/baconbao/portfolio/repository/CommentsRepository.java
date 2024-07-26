package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    Optional<Comments> findById(Integer integer);
}
