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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blv.tracker.application.dto.ProjectDto;
import com.blv.tracker.application.service.ProjectService;

@RestController
@RequestMapping("api/projects/")
public class ProjectController {
	
	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		super();
		this.projectService = projectService;
	}
	

	@PostMapping()
	public ResponseEntity<ProjectDto> createPost(@RequestBody ProjectDto postDto) {
		return new ResponseEntity<>(projectService.createProject(postDto), HttpStatus.CREATED);
	}

	@GetMapping()
	public List<ProjectDto> getAllProjects(@RequestParam (value="pageNo", defaultValue="0",required=false) int pageNo,
			@RequestParam (value="pageSize", defaultValue="10",required=false) int pageSize) {
		return projectService.getAllProjects(pageNo,pageSize);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjectDto> getProjectById(@PathVariable(name="id") Long id) {
		return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto,@PathVariable(name="id") Long id){
		ProjectDto projectResponse= projectService.updateProject(projectDto, id);
		return new ResponseEntity<>(projectResponse,HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable(name="id") Long id){
		projectService.deleteProjectById(id);
		return new ResponseEntity<>("Project with id "+id+" deleted successfully.",HttpStatus.OK);
	}
	

}
