package com.eys.cardsms.factories;

import java.util.Calendar;

import com.eys.cardsms.enums.BulkProcessEnum;
import com.eys.cardsms.models.BulkProcess;

public class BulkProcessFactory {
	
	public static BulkProcess create(String fileName) {
		
		BulkProcess bulkProcess = BulkProcess.builder().
				bulk_process_id(null).
				create_date(Calendar.getInstance().getTime()).
				file_name(fileName).status(BulkProcessEnum.PENDING).build();
		
		return bulkProcess;
	}

}
