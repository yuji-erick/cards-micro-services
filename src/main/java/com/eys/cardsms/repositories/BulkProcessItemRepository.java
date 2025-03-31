package com.eys.cardsms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eys.cardsms.models.BulkProcessItem;

@Repository
public interface BulkProcessItemRepository extends JpaRepository<BulkProcessItem, Long> {

}
