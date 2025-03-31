package com.eys.cardsms.controllers;

import java.util.Optional;

import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eys.cardsms.enums.BulkProcessEnum;
import com.eys.cardsms.models.BulkProcess;
import com.eys.cardsms.services.BulkProcessService;

@RestController
@RequestMapping({"/bulkprocess"})
public class BulkProcessController {
	
	private final BulkProcessService bulkProcessService;
	
	private final TaskExecutor taskExecutor;
	
	public BulkProcessController(BulkProcessService bulkProcessService, TaskExecutor taskExecutor) {
		this.bulkProcessService = bulkProcessService;
		this.taskExecutor = taskExecutor;
	}
	
	@PostMapping()
	public ResponseEntity<?> bulkprocess(@RequestParam("file") MultipartFile file){
		try {
			if (file.isEmpty() || !file.getOriginalFilename().endsWith(".txt")) {
	            return ResponseEntity.badRequest().build();
	        }
			
			final BulkProcess bulkProcess = bulkProcessService.saveBulkProcess(file.getOriginalFilename());
			
			bulkProcess.setStatus(BulkProcessEnum.PROCESSING);
			
			bulkProcessService.update(bulkProcess);
			
			taskExecutor.execute(() -> bulkProcessService.processFile(bulkProcess, file));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(bulkProcess);
		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getBulkprocess(@PathVariable("id") Long id){
		try {
			
			Optional<BulkProcess> optBulkProcess = bulkProcessService.findById(id);
			
			if(optBulkProcess.isPresent()) {
				return ResponseEntity.ok(optBulkProcess.get());
			}
			else {
				return ResponseEntity.notFound().build();
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
	}

}
