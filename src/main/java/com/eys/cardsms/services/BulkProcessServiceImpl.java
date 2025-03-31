package com.eys.cardsms.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eys.cardsms.dtos.CardDTO;
import com.eys.cardsms.enums.BulkProcessEnum;
import com.eys.cardsms.enums.BulkProcessItemStatusEnum;
import com.eys.cardsms.enums.CardCreationTypeEnum;
import com.eys.cardsms.exceptions.DuplicatedCardException;
import com.eys.cardsms.exceptions.InvalidCardException;
import com.eys.cardsms.factories.BulkProcessFactory;
import com.eys.cardsms.factories.BulkProcessItemFactory;
import com.eys.cardsms.factories.CardFactory;
import com.eys.cardsms.models.BulkProcess;
import com.eys.cardsms.models.BulkProcessItem;
import com.eys.cardsms.models.Card;
import com.eys.cardsms.repositories.BulkProcessItemRepository;
import com.eys.cardsms.repositories.BulkProcessRepository;
import com.eys.cardsms.utils.txtreader.TXTField;
import com.eys.cardsms.utils.txtreader.TXTReader;
import com.eys.cardsms.utils.txtreader.TXTResult;

@Service
public class BulkProcessServiceImpl implements BulkProcessService {
	
	private static final String LINE_ID = "Line ID";
	private static final String BULK_ID = "Bulk ID";
	private static final String CARD_NUMBER = "Card Number";
	private static final String FILLER = "Filler";
	
	private static final String HEADER_NAME = "Header Name";
	private static final String DATE = "Date";
	private static final String BULK_PROCESS = "Bulk Process";
	private static final String QUANTITY = "Quantity";
	
	private BulkProcessRepository bulkProcessRepository;
	private BulkProcessItemRepository bulkProcessItemRepository;
	private CardService cardService;
	
	public BulkProcessServiceImpl(BulkProcessRepository bulkProcessRepository,
			BulkProcessItemRepository bulkProcessItemRepository,
			CardService cardService) {
		
		this.bulkProcessRepository = bulkProcessRepository;
		this.bulkProcessItemRepository = bulkProcessItemRepository;
		this.cardService = cardService;
	}
	
	@Override
	public Optional<BulkProcess> findById(Long id) {
		return bulkProcessRepository.findById(id);
	}
	
	@Override
	public BulkProcess saveBulkProcess(String fileName) {
		
		BulkProcess bulkProcess = BulkProcessFactory.create(fileName);
		
		bulkProcess = bulkProcessRepository.save(bulkProcess);
		
		return bulkProcess;
	}
	
	@Override
	public BulkProcess update(BulkProcess bulkProcess) {
		return bulkProcessRepository.save(bulkProcess);
	}
	
	@Override
	public void processFile(BulkProcess bulkProcess, MultipartFile file) {
		try {
			
			byte[] bytes = file.getBytes();
			Path path = Paths.get(file.getOriginalFilename());
			Files.write(path, bytes);
			
			List<TXTField> headerLayout = createHeaderLayout();
			List<TXTField> detailsLayout = createDetailsLayout();
			
			TXTResult result = TXTReader.readCSV(path.toFile(), headerLayout, detailsLayout);
			
			result.getDetalhes().stream().forEach(e -> saveBulkProcessItens(e, bulkProcess));
			
			bulkProcess.setStatus(BulkProcessEnum.PROCESSED);
			
			bulkProcessRepository.save(bulkProcess);
		}
		catch(Exception e) {
			
			bulkProcess.setStatus(BulkProcessEnum.ERROR);
			
			bulkProcessRepository.save(bulkProcess);
			
			System.out.println(e);
		}
	}
	

	private void saveBulkProcessItens(Map<String, String> lines, BulkProcess bulkProcess) {
		
		CardDTO cardDTO = CardFactory.createDTO(
				lines.get(CARD_NUMBER), CardCreationTypeEnum.BULK_PROCESS);
		
		Card card = null;
		BulkProcessItem bulkProcessItem = null;
		
		try {
			card = cardService.save(cardDTO);
			
			bulkProcessItem = BulkProcessItemFactory.
					create(lines.get(LINE_ID), lines.get(BULK_ID), lines.get(CARD_NUMBER), 
							BulkProcessItemStatusEnum.OK, bulkProcess, card);
		} 
		catch (DuplicatedCardException e) {
			bulkProcessItem = BulkProcessItemFactory.
					create(lines.get(LINE_ID), lines.get(BULK_ID), lines.get(CARD_NUMBER), 
							BulkProcessItemStatusEnum.DUPLICATED, bulkProcess, null);
		} 
		catch (InvalidCardException e) {
			bulkProcessItem = BulkProcessItemFactory.
					create(lines.get(LINE_ID), lines.get(BULK_ID), lines.get(CARD_NUMBER), 
							BulkProcessItemStatusEnum.INVALID_NUMBER, bulkProcess, null);
		}
		
		bulkProcessItemRepository.save(bulkProcessItem);
	}

	private List<TXTField> createHeaderLayout() {
		List<TXTField> layout = new ArrayList<>();
		
        layout.add(TXTField.builder().fieldName(HEADER_NAME).initialPosition(0).size(29).build());
        layout.add(TXTField.builder().fieldName(DATE).initialPosition(29).size(8).build());
        layout.add(TXTField.builder().fieldName(BULK_PROCESS).initialPosition(30).size(8).build());
        layout.add(TXTField.builder().fieldName(QUANTITY).initialPosition(38).size(6).build());
        
        return layout;
	}
	
	private List<TXTField> createDetailsLayout() {
		List<TXTField> layout = new ArrayList<>();
		
        layout.add(TXTField.builder().fieldName(LINE_ID).initialPosition(0).size(1).build());
        layout.add(TXTField.builder().fieldName(BULK_ID).initialPosition(1).size(6).build());
        layout.add(TXTField.builder().fieldName(CARD_NUMBER).initialPosition(7).size(16).build());
        layout.add(TXTField.builder().fieldName(FILLER).initialPosition(23).size(28).build());
        
        return layout;
	}
}
