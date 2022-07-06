package com.blv.tracker.application.service;

import java.util.List;

import com.blv.tracker.application.dto.TaskDto;

public interface TaskService {
	
	TaskDto createTask(Long projectId,Long floorId, TaskDto taskDto );
	
	List<TaskDto> getTasksByFloorId(Long projectId,Long floorId);
	
	TaskDto getTaskById(Long projectId,Long floorId, Long taskId);
	
	TaskDto updateTask(Long projectId,Long floorId,Long taskId,TaskDto taskDto);
	
	void deleteTask(Long projectId,Long FloorId,Long taskId);

}
