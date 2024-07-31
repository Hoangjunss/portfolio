package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Comments;
import com.baconbao.portfolio.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    Optional<Comments> findById(Integer integer);
    List<Comments> findByProfile(Profile profile);
    @Query("select c from Comments c inner join Profile p where p.user.id= :id ")
    List<Comments> findByUserId(Integer id);
}
