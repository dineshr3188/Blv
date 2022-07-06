package com.blv.tracker.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blv.tracker.application.dto.TaskDto;
import com.blv.tracker.application.service.TaskService;

@RestController
@RequestMapping("api/projects/{projectId}/floor/{floorId}/task/")
public class TaskController {
	
	private TaskService taskService;
	

	public TaskController(TaskService taskService) {
		super();
		this.taskService = taskService;
	}
	

	@PostMapping()
	public ResponseEntity<TaskDto> createTask(@PathVariable(value = "projectId") Long projectId,@PathVariable(value = "floorId") Long floorId,@RequestBody TaskDto taskDto) {
		return new ResponseEntity<>(taskService.createTask(projectId,floorId, taskDto), HttpStatus.CREATED);
	}

	@GetMapping()
	public List<TaskDto> getTasksByFloorId(@PathVariable(value = "projectId") Long projectId,@PathVariable(value = "floorId") Long floorId) {
		return taskService.getTasksByFloorId(projectId,floorId);
	}
	
	@GetMapping("/{taskId}")
	public ResponseEntity<TaskDto> getTasksById(@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "floorId") Long floorId,@PathVariable(value = "taskId") Long taskId) {
		TaskDto taskDto = taskService.getTaskById(projectId, floorId,taskId);
		return new ResponseEntity<>(taskDto, HttpStatus.OK);
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<TaskDto> updateTask(@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "floorId") Long floorId,@PathVariable(value = "taskId") Long taskId, @RequestBody TaskDto taskDto) {
		return new ResponseEntity<>(taskService.updateTask(projectId, floorId,taskId, taskDto), HttpStatus.OK);
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<String> deleteteFloor(@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "floorId") Long floorId,@PathVariable(value = "taskId") Long taskId) {
		taskService.deleteTask(projectId, floorId,taskId);
		return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
	}
	

}
