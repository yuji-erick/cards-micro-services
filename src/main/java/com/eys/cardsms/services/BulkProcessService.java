package com.eys.cardsms.services;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.eys.cardsms.models.BulkProcess;

public interface BulkProcessService {
	
	Optional<BulkProcess> findById(Long id);

	BulkProcess saveBulkProcess(String fileName);
	
	void processFile(BulkProcess bulkProcess, MultipartFile file);

	BulkProcess update(BulkProcess bulkProcess);

}
