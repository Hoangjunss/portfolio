package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findById(Integer integer);
}
