package com.blv.tracker.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blv.tracker.application.entity.Floor;
import com.blv.tracker.application.entity.Images;
import com.blv.tracker.application.entity.Project;
import com.blv.tracker.application.entity.Task;
import com.blv.tracker.application.exception.ProjectAPIException;
import com.blv.tracker.application.exception.ResourceNotFoundException;
import com.blv.tracker.application.repository.FloorRepository;
import com.blv.tracker.application.repository.ImagesRepository;
import com.blv.tracker.application.repository.ProjectRepository;
import com.blv.tracker.application.repository.TaskRepository;
import com.blv.tracker.application.service.ImagesService;

@Service
public class ImagesServiceImpl implements ImagesService{

	@Autowired
	private ImagesRepository imagesRepository;
	private ProjectRepository projectRepository;
	private FloorRepository floorRepository;
	private TaskRepository taskRepository;
	
	
	
	public ImagesServiceImpl(ImagesRepository imagesRepository, ProjectRepository projectRepository,
			FloorRepository floorRepository, TaskRepository taskRepository) {
		super();
		this.imagesRepository = imagesRepository;
		this.projectRepository = projectRepository;
		this.floorRepository = floorRepository;
		this.taskRepository = taskRepository;
	}
	@Override
	public Images saveFile(Long projectId, Long floorId,Long taskId,MultipartFile file) {
		
		Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
		Floor floor = floorRepository.findById(floorId).orElseThrow(() -> new ResourceNotFoundException("Floor", "id", floorId));
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
		
		floor.setProject(project);
		task.setFloor(floor);
		String docName=file.getOriginalFilename();
		try {
			Images images= new Images(docName,file.getContentType(),file.getBytes());
			images.setTask(task);
			return imagesRepository.save(images);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Optional<Images> getFile(Long id){
		return imagesRepository.findById(id);
	}
	@Override
	public List<Images> getFiles(){
		return imagesRepository.findAll();
	}
	@Override
	public void deleteImage(Long projectId, Long floorId, Long taskId, Long fileId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
		Floor floor = floorRepository.findById(floorId)
				.orElseThrow(() -> new ResourceNotFoundException("Floor", "Floor Id", floorId));
		if (!floor.getProject().getId().equals(project.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "comment doesnt beLong to this post");
		}
		Task task= taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
		if (!task.getFloor().getId().equals(floor.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Task doesnt belong to this Floor");
		}
		Images image = imagesRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("Image", "imageId", fileId));
		if (!image.getTask().getId().equals(task.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Image doesnt belong to this task");
		}
		imagesRepository.deleteById(fileId);
	}
	
	
	
}
