package com.blv.tracker.application.service;

import java.util.List;

import com.blv.tracker.application.dto.ProjectDto;


public interface ProjectService {
	
	ProjectDto createProject(ProjectDto projectDto);
	List<ProjectDto> getAllProjects(int pageNo, int pageSize);
	ProjectDto getProjectById(Long id);
	ProjectDto updateProject(ProjectDto projectDto, Long id);
	void deleteProjectById(Long id);

}
