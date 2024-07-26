package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

}
