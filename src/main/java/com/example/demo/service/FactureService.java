package com.example.demo.service;

import com.example.demo.dto.FactureDTO;
import com.example.demo.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private FactureMapper factureMapper;

    public List<FactureDTO> findAllFactures() {
        return factureRepository.findAll().stream().map(factureMapper::map).collect(toList());
    }

    public FactureDTO findById(Long id) {
        return factureRepository.findById(id)
                .map(factureMapper::map)
                .orElseThrow(() ->
                        new IllegalArgumentException("Facture inconnu " + id)
                );
    }
    
    // Méthode récupérant les factures par client
    public List<FactureDTO> findFacturesByClient(Long clientId){
    	return factureRepository.findByClientId(clientId).stream().map(factureMapper::map).collect(toList()); 
    }

}
