package com.blv.tracker.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blv.tracker.application.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,Long>{

}
