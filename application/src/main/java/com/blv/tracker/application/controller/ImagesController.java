package com.blv.tracker.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blv.tracker.application.entity.Images;
import com.blv.tracker.application.service.ImagesService;

@RestController
@RequestMapping("api/projects/{projectId}/floor/{floorId}/task/{taskId}/images")
public class ImagesController {

	@Autowired
	private ImagesService imagesService;
	
	
	@PostMapping("/upload")
	public String uploadToDB(@PathVariable(value = "projectId") Long projectId,@PathVariable(value = "floorId") Long floorId,@PathVariable(value = "taskId") Long taskId, @RequestParam("files") MultipartFile[] files) {
		for(MultipartFile file:files) {
			imagesService.saveFile(projectId,floorId,taskId,file);
		}
		return "redirect:/";
	}
	
	@GetMapping("/download/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) {
		Images image=imagesService.getFile(fileId).get();
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+image.getName()+"\"")
		.body(new ByteArrayResource(image.getFile()));
	}
	@DeleteMapping("/{fileId}")
	public ResponseEntity<String> deleteImage(@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "floorId") Long floorId,@PathVariable(value = "taskId") Long taskId,@PathVariable(value = "fileId") Long fileId) {
		imagesService.deleteImage(projectId, floorId,taskId,fileId);
		return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
	}
}
