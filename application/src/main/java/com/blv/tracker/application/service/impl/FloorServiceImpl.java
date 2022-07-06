package com.blv.tracker.application.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blv.tracker.application.dto.FloorsDto;
import com.blv.tracker.application.entity.Floor;
import com.blv.tracker.application.entity.Project;
import com.blv.tracker.application.exception.ProjectAPIException;
import com.blv.tracker.application.exception.ResourceNotFoundException;
import com.blv.tracker.application.repository.FloorRepository;
import com.blv.tracker.application.repository.ProjectRepository;
import com.blv.tracker.application.service.FloorService;

@Service
public class FloorServiceImpl implements FloorService{
	
	private ProjectRepository projectRepository;
	private FloorRepository floorRepository;

	public FloorServiceImpl(ProjectRepository projectRepository, FloorRepository floorRepository) {
		super();
		this.projectRepository = projectRepository;
		this.floorRepository = floorRepository;
	}
	
	public Floor mapToEntity(FloorsDto floorsDto) {
		Floor floor = new Floor();
		floor.setId(floorsDto.getId());
		floor.setName(floorsDto.getName());
		return floor;
	}
	
	public FloorsDto mapToDto(Floor floor) {
		FloorsDto floorsDto= new FloorsDto();
		floorsDto.setId(floor.getId());
		floorsDto.setName(floor.getName());
		return floorsDto;
	}

	@Override
	public FloorsDto createFloor(Long projectId, FloorsDto FloorDto) {
		Floor floor = mapToEntity(FloorDto);
		// get post entity by id
		Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
		floor.setProject(project);
		// comment to DB
		Floor newFloor = floorRepository.save(floor);
		return mapToDto(newFloor);
	}

	@Override
	public List<FloorsDto> getFloorsByProjectId(Long projectId) {
		List<Floor> floors = floorRepository.findByProjectId(projectId);
		return floors.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public FloorsDto getFloorById(Long projectId, Long floorId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

		Floor floor = floorRepository.findById(floorId)
				.orElseThrow(() -> new ResourceNotFoundException("Floor", "floorId", floorId));

		if (!floor.getProject().getId().equals(project.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Floor doesnt belong to this project");
		}
		return mapToDto(floor);
	}

	@Override
	public FloorsDto updateFloor(Long projectId, Long floorId, FloorsDto FloorDto) {
		Project post = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Projectt", "id", projectId));

		Floor floor = floorRepository.findById(floorId)
				.orElseThrow(() -> new ResourceNotFoundException("Floor", "floorId", floorId));

		if (!floor.getProject().getId().equals(post.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "Floor doesnt beLong to this project");
		}
		floor.setId(FloorDto.getId());
		floor.setName(FloorDto.getName());
		Floor updatedFloor=floorRepository.save(floor);
		return mapToDto(updatedFloor);
	}

	@Override
	public void deleteFloor(Long projectId, Long FloorId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
		Floor floor = floorRepository.findById(FloorId)
				.orElseThrow(() -> new ResourceNotFoundException("Floor", "Floor Id", FloorId));
		if (!floor.getProject().getId().equals(project.getId())) {
			throw new ProjectAPIException(HttpStatus.BAD_REQUEST, "comment doesnt beLong to this post");
		}
		floorRepository.deleteById(FloorId);
	}

}
