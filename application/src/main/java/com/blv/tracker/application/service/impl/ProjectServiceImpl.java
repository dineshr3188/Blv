package com.blv.tracker.application.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blv.tracker.application.dto.ProjectDto;
import com.blv.tracker.application.entity.Project;
import com.blv.tracker.application.exception.ResourceNotFoundException;
import com.blv.tracker.application.repository.ProjectRepository;
import com.blv.tracker.application.service.ProjectService;


@Service
public class ProjectServiceImpl implements ProjectService{
	
	private ProjectRepository projectRepo;
	
	public ProjectServiceImpl(ProjectRepository projectRepo) {
		super();
		this.projectRepo = projectRepo;
	}

	private Project mapToEntity(ProjectDto projectDto) {
		Project project = new Project();
		project.setName(projectDto.getName());
		project.setDescription(projectDto.getDescription());
		return project;
	}
	
	private ProjectDto mapToDto(Project project) {
		ProjectDto projectDto = new ProjectDto();
		projectDto.setId(project.getId());
		projectDto.setName(project.getName());
		projectDto.setDescription(project.getDescription());
		return projectDto;
	}

	@Override
	public ProjectDto createProject(ProjectDto projectDto) {
		Project project = mapToEntity(projectDto);
		Project newProject =projectRepo.save(project);
		ProjectDto newProjectDto=mapToDto(newProject);
		return newProjectDto;
		
	}

	@Override
	public List<ProjectDto> getAllProjects(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
				Pageable pageable = PageRequest.of(pageNo, pageSize);
				Page<Project> projects= projectRepo.findAll(pageable);	
				List<Project> listOfProjects= projects.getContent();
				return listOfProjects.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}

	@Override
	public ProjectDto getProjectById(Long id) {
		Project project =projectRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Project","id",id));
		return mapToDto(project);
	}

	@Override
	public ProjectDto updateProject(ProjectDto projectDto, Long id) {
		Project project =projectRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Project","id",id));
		project.setId(projectDto.getId());
		project.setName(projectDto.getName());
		project.setDescription(projectDto.getDescription());
		Project updatedPost= projectRepo.save(project);
		return mapToDto(updatedPost);
	}

	@Override
	public void deleteProjectById(Long id) {
		Project project =projectRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Project","id",id));
		projectRepo.deleteById(id);
	}

	
	
}
