package com.eys.cardsms.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eys.cardsms.dtos.CardDTO;
import com.eys.cardsms.exceptions.DuplicatedCardException;
import com.eys.cardsms.exceptions.InvalidCardException;
import com.eys.cardsms.models.Card;

public interface CardService {
	
	Card save(CardDTO cardDTO) throws DuplicatedCardException, InvalidCardException;

	Page<Card> getAllCards(Pageable pageable);
	
	Optional<Card> findByCardNumber(String cardNumber);

}
