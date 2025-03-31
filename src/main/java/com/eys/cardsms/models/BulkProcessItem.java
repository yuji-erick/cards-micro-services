package com.eys.cardsms.models;

import com.eys.cardsms.enums.BulkProcessItemStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bulk_process_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkProcessItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bulk_process_item_id;
	
	@ManyToOne
	@JoinColumn(name = "bulk_process_id")
	private BulkProcess bulkProcess;
	
	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;
	
	private String card_number;
	
	private String line_id;
	
	private String bulk_id;
	
    @Enumerated(EnumType.STRING)
    private BulkProcessItemStatusEnum status; 

}
