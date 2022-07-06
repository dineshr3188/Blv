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

import com.blv.tracker.application.dto.FloorsDto;
import com.blv.tracker.application.service.FloorService;

@RestController
@RequestMapping("api/projects/{projectId}/floor/")
public class FloorController {

	private FloorService floorService;

	public FloorController(FloorService floorService) {
		this.floorService = floorService;
	}


	@PostMapping()
	public ResponseEntity<FloorsDto> createFloor(@PathVariable(value = "projectId") Long projectId,
			@RequestBody FloorsDto floorDto) {
		return new ResponseEntity<>(floorService.createFloor(projectId, floorDto), HttpStatus.CREATED);
	}

	@GetMapping()
	public List<FloorsDto> getFloorsByProjectId(@PathVariable(value = "projectId") Long projectId) {
		return floorService.getFloorsByProjectId(projectId);
	}

	@GetMapping("/{floorId}")
	public ResponseEntity<FloorsDto> getFloorsById(@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "floorId") Long floorId) {
		FloorsDto floorDto = floorService.getFloorById(projectId, floorId);
		return new ResponseEntity<>(floorDto, HttpStatus.OK);
	}


	@PutMapping("/{floorId}")
	public ResponseEntity<FloorsDto> updateFloor(@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "floorId") Long floorId, @RequestBody FloorsDto floorDto) {
		return new ResponseEntity<>(floorService.updateFloor(projectId, floorId, floorDto), HttpStatus.OK);
	}


	@DeleteMapping("/{floorId}")
	public ResponseEntity<String> deleteteFloor(@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "floorId") Long floorId) {
		floorService.deleteFloor(projectId, floorId);
		return new ResponseEntity<>("Floor deleted successfully", HttpStatus.OK);
	}

}
