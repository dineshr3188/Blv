package com.blv.tracker.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.blv.tracker.application.entity.Images;

public interface ImagesService {

	

	Optional<Images> getFile(Long id);

	List<Images> getFiles();

	Images saveFile(Long projectId, Long floorId, Long taskId, MultipartFile file);


	void deleteImage(Long projectId, Long floorId, Long taskId, Long fileId);

}
