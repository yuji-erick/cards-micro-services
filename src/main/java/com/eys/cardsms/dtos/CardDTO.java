package com.eys.cardsms.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardDTO {
	
	private Long id;
	private String cardNumber;
	private String creationType;	

}
