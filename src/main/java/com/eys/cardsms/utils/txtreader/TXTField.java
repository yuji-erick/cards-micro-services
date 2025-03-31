package com.eys.cardsms.utils.txtreader;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TXTField {
	
	private String fieldName;
	private int initialPosition;
	private int size;

}
