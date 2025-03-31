package com.eys.cardsms.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eys.cardsms.dtos.CardDTO;
import com.eys.cardsms.enums.CardCreationTypeEnum;
import com.eys.cardsms.exceptions.DuplicatedCardException;
import com.eys.cardsms.exceptions.InvalidCardException;
import com.eys.cardsms.models.Card;
import com.eys.cardsms.services.CardService;

@RestController
@RequestMapping({"/cards"})
public class CardController {
	
	private final CardService cardService;
	
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}
	
	@PostMapping
	public ResponseEntity<?> saveCard(@RequestBody CardDTO cardDTO){
		try {
			cardDTO.setCreationType(CardCreationTypeEnum.UNIQUE.name());
			Card card = cardService.save(cardDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(card);
		}
		catch(DuplicatedCardException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		catch(InvalidCardException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<?> getCards(Pageable pageable){
		try {
			if(pageable.getPageSize() > 100) {
				return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Payload too large, Maximum allowed per page: 100");
			}
			Page<Card> cards = cardService.getAllCards(pageable);
			 return ResponseEntity.ok(cards);
		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(value = "/card/{cardNumber}")
	public ResponseEntity<?> getCard(@PathVariable("cardNumber") String cardNumber){
		try {
			Optional<Card> optCard = cardService.findByCardNumber(cardNumber);
			
			if(optCard.isPresent()) {
				return ResponseEntity.ok(optCard.get());	
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
