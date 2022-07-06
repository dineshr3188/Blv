package com.blv.tracker.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.blv.tracker.application.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{
	List<Task> findByFloorId(Long floorId);
}
