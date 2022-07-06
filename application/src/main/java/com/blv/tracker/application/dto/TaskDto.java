package com.blv.tracker.application.dto;

import lombok.Data;

@Data
public class TaskDto {
	
	private Long id;
	private String name;
	private String[] images;
	private Long percentage;

}
