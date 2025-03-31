package com.eys.cardsms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eys.cardsms.models.Card;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	
	@Query("select c from Card c where card_number = :cardNumber")
	Optional<Card> findByCardNumber(String cardNumber);

}
