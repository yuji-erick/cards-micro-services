package com.eys.cardsms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eys.cardsms.models.BulkProcess;

@Repository
public interface BulkProcessRepository extends JpaRepository<BulkProcess, Long> {

}
