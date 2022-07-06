package com.blv.tracker.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blv.tracker.application.entity.Floor;


public interface FloorRepository extends JpaRepository<Floor,Long>{
	List<Floor> findByProjectId(Long projectId);

}
