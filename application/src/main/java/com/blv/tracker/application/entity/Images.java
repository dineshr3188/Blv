package com.blv.tracker.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Images {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="type")
	private String type;
	@Column
    @Lob
    private byte[] file;	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id",nullable=false)
	private Task task;
	public Images(String name, String type, byte[] file) {
		super();
		this.name = name;
		this.type = type;
		this.file = file;
	}

	
}
