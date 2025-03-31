package com.eys.cardsms.factories;

import java.util.Calendar;

import com.eys.cardsms.dtos.CardDTO;
import com.eys.cardsms.enums.CardCreationTypeEnum;
import com.eys.cardsms.models.Card;

public class CardFactory {
	
	public static Card createFromDTO(CardDTO cardDTO) {
		
		Card card = Card.builder().
				card_id(cardDTO.getId()).
				create_date(Calendar.getInstance().getTime()).
				card_number(cardDTO.getCardNumber()).
				creation_type(CardCreationTypeEnum.valueOf(cardDTO.getCreationType())).
				build();
		
		return card;
	}
	
	public static Card create(String cardNumber, CardCreationTypeEnum cardCreationType) {
		
		Card card = Card.builder().
				card_id(null).
				create_date(Calendar.getInstance().getTime()).
				card_number(cardNumber).
				creation_type(cardCreationType).
				build();
		
		return card;
	}
	
	public static CardDTO createDTO(String cardNumber, CardCreationTypeEnum cardCreationType) {
		
		CardDTO cardDTO = CardDTO.builder().
				id(null).
				cardNumber(cardNumber).
				creationType(cardCreationType.name()).
				build();
		
		return cardDTO;
	}
}
