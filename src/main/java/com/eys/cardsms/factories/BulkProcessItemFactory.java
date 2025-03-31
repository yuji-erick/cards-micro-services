package com.eys.cardsms.factories;

import com.eys.cardsms.enums.BulkProcessItemStatusEnum;
import com.eys.cardsms.models.BulkProcess;
import com.eys.cardsms.models.BulkProcessItem;
import com.eys.cardsms.models.Card;

public class BulkProcessItemFactory {
	
	public static BulkProcessItem create(String lineId, String bulkId, String cardNumber, 
			BulkProcessItemStatusEnum status, BulkProcess bulkProcess, Card card) {
		
		BulkProcessItem bulkProcessItem = BulkProcessItem.builder().
				bulk_process_item_id(null).
				card(card).
				card_number(cardNumber).
				status(status).
				line_id(lineId).
				bulk_id(bulkId).
				bulkProcess(bulkProcess).build();
		
		return bulkProcessItem;
	}

}
