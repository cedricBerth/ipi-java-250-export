package com.example.demo.repository;

import com.example.demo.dto.FactureDTO;
import com.example.demo.entity.Facture;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

	Collection<Facture> findByClientId(Long clientId);
	
}
