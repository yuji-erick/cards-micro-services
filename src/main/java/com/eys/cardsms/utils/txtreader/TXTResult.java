package com.eys.cardsms.utils.txtreader;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TXTResult {
	
	private Map<String, String> header;
    private List<Map<String, String>> detalhes;

}
