package com.eys.cardsms.utils.txtreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TXTReader {
	
    public static TXTResult readCSV(File file, List<TXTField> headerLayout, List<TXTField> detailsLayout) throws IOException {
    	
        Map<String, String> header = new HashMap<>();
        List<Map<String, String>> detalhes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                for (TXTField field : headerLayout) {
                    header.put(field.getFieldName(), headerLine.substring(field.getInitialPosition(), field.getInitialPosition() + field.getSize()).trim());
                }

                String linhaDetalhe;
                while ((linhaDetalhe = br.readLine()) != null && !linhaDetalhe.startsWith("LOTE")) {
                    Map<String, String> detalhe = new HashMap<>();
                    for (TXTField field : detailsLayout) {
                    	try {
                    		detalhe.put(field.getFieldName(), linhaDetalhe.substring(field.getInitialPosition(), field.getInitialPosition() + field.getSize()).trim());
                    	}
                    	catch(Exception e) {
                    		detalhe.put(field.getFieldName(), new String());
                    	}
                    }
                    detalhes.add(detalhe);
                }
            }
        }

        return new TXTResult(header, detalhes);
    }

}
