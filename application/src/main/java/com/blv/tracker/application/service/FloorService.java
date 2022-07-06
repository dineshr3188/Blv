package com.blv.tracker.application.service;

import java.util.List;

import com.blv.tracker.application.dto.FloorsDto;



public interface FloorService {
	
	FloorsDto createFloor(Long projectId,FloorsDto FloorDto );
	
	List<FloorsDto> getFloorsByProjectId(Long projectId);
	
	FloorsDto getFloorById(Long projectId,Long FloorId);
	
	FloorsDto updateFloor(Long projectId,Long FloorId,FloorsDto FloorDto );
	
	void deleteFloor(Long projectId,Long FloorId);

}
