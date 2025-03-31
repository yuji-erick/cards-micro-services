package com.eys.cardsms.models;

import java.beans.Transient;
import java.util.Date;
import java.util.regex.Pattern;

import com.eys.cardsms.enums.CardCreationTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long card_id;

    private Date create_date;
    private String card_number;
    
    @Enumerated(EnumType.STRING)
    private CardCreationTypeEnum creation_type;
    
    @Transient
    public boolean isCardNumberValid() {
    	if(card_number != null && !card_number.isEmpty() && card_number.length() == 16) {
    		
    		boolean isMatch = Pattern.compile("^\\d+$")
    	               .matcher(card_number).find();
    		
    		if(isMatch) {
    			return true;
    		}
    	}
    	return false;
    }
}
