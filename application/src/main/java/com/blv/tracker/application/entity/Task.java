package com.blv.tracker.application.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="images")
	private String[] images;
	@Column(name="percentage")
	private Long percentage;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="floor_id",nullable=false)
	private Floor floor;

}
