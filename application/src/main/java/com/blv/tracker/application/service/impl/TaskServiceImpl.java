package com.blv.tracker.application.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blv.tracker.application.dto.FloorsDto;
import com.blv.tracker.application.dto.TaskDto;
import com.blv.tracker.application.entity.Floor;
import com.blv.tracker.application.entity.Project;
import com.blv.tracker.application.entity.Task;
import com.blv.tracker.application.exception.ProjectAPIException;
import com.blv.tracker.application.exception.ResourceNotFoundException;
import com.blv.tracker.application.repository.FloorRepository;
import com.blv.tracker.application.repository.ProjectRepository;
import com.blv.tracker.application.repository.TaskRepository;
import com.blv.tracker.application.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	private ProjectRepository projectRepository;
	private FloorRepository floorRepository;
	private TaskRepository taskRepository;

	public TaskServiceImpl(ProjectRepository projectRepository, FloorRepository floorRepository,
			TaskRepository taskRepository) {
		super();
		this.projectRepository = projectRepository;
		this.floorRepository = floorRepository;
		this.taskRepository = taskRepository;
	}

	public Task mapToEntity(TaskDto taskDto) {
		Task task = new Task();
		task.setId(taskDto.getId());
		task.setName(taskDto.getName());
		task.setPercentage(taskDto.getPercentage());
		task.setImages(taskDto.getImages());
		return task;
	}
	
	public TaskDto mapToDto(Task task) {
		TaskDto taskDto = new TaskDto();
		taskDto.setId(task.getId());
		taskDto.setName(task.getName());
		taskDto.setPercentage(task.getPercentage());
		taskDto.setImages(task.getImages());
		return taskDto;
	}
	
	@Override
	public TaskDto createTask(Long projectId, Long floorId, TaskDto taskDto) {
		Task task= mapToEntity(taskDto);
		Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
		Floor floor = floorRepository.findById(floorId).orElseThrow(() -> new ResourceNotFoundException("Floor", "id", floorId));
		floor.setProject(project);
		task.setFloor(floor);
		Task updatedTask = taskRepository.save(task);
		return mapToDto(updatedTask);
	}
	
	@Override
	public List<TaskDto> getTasksByFloorId(Long projectId, Long floorId) {
		//List<Floor> floors = floorRepository.findByFloorId(projectId);
		List<Task> tasks = taskRepository.findByFloorId(floorId);
		return tasks.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTaskById(Long projectId, Long floorId, Long taskId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

		Floor floor = floorRepository.findById(floorId)
				.orElseThrow(() -> new ResourceNotFoundException("Floor", "floorId", floorId));

		if (!floor.getProject().getId().equals(project.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Floor doesnt belong to this project");
		}
		Task task= taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
		if (!task.getFloor().getId().equals(floor.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Task doesnt belong to this Floor");
		}
		return mapToDto(task);
	}

	@Override
	public TaskDto updateTask(Long projectId, Long floorId, Long taskId, TaskDto taskDto) {
		Project post = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Projectt", "id", projectId));

		Floor floor = floorRepository.findById(floorId)
				.orElseThrow(() -> new ResourceNotFoundException("Floor", "floorId", floorId));

		if (!floor.getProject().getId().equals(post.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Floor doesnt beLong to this project");
		}
		Task task= taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
		if (!task.getFloor().getId().equals(floor.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Task doesnt belong to this Floor");
		}
//		floor.setId(FloorDto.getId());
//		floor.setName(FloorDto.getName());
//		Floor updatedFloor=floorRepository.save(floor);
		task.setId(taskDto.getId());
		Task updatedTask=taskRepository.save(task);
		return mapToDto(updatedTask);
	}

	@Override
	public void deleteTask(Long projectId, Long FloorId, Long taskId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
		Floor floor = floorRepository.findById(FloorId)
				.orElseThrow(() -> new ResourceNotFoundException("Floor", "Floor Id", FloorId));
		if (!floor.getProject().getId().equals(project.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "comment doesnt beLong to this post");
		}
		Task task= taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
		if (!task.getFloor().getId().equals(floor.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Task doesnt belong to this Floor");
		}
		taskRepository.deleteById(taskId);
	}

	

}
