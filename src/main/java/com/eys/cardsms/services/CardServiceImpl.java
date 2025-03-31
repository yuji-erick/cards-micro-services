package com.eys.cardsms.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eys.cardsms.dtos.CardDTO;
import com.eys.cardsms.exceptions.DuplicatedCardException;
import com.eys.cardsms.exceptions.InvalidCardException;
import com.eys.cardsms.factories.CardFactory;
import com.eys.cardsms.models.Card;
import com.eys.cardsms.repositories.CardRepository;

@Service
public class CardServiceImpl implements CardService {
	
	private final CardRepository cardRepository;
	
	public CardServiceImpl(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Override
	public Card save(CardDTO cardDTO) throws DuplicatedCardException, InvalidCardException {
		
		Card card = CardFactory.createFromDTO(cardDTO);
		
		if(card.isCardNumberValid()) {
			Optional<Card> optCard = cardRepository.findByCardNumber(card.getCard_number());
			
			if(!optCard.isPresent()) {
				return cardRepository.save(card);
			}
			else {
				throw new DuplicatedCardException("Card " + cardDTO.getCardNumber() + " already registered!");
			}
		}
		else {
			throw new InvalidCardException("Card " + cardDTO.getCardNumber() + " invalid!");
		}
	}

	@Override
	public Page<Card> getAllCards(Pageable pageable) {
		return cardRepository.findAll(pageable);
	}

	@Override
	public Optional<Card> findByCardNumber(String cardNumber) {
		return cardRepository.findByCardNumber(cardNumber);
	}

}
