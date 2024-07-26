package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
