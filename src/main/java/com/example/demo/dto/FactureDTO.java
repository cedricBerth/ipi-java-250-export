package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayne on 09/04/2018.
 */
public class FactureDTO {

    private Long id;

    private ClientDTO client;
    
    private List<LigneFactureDTO> ligneFactures = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public List<LigneFactureDTO> getLigneFactures() {
        return ligneFactures;
    }

    public void setLigneFactures(List<LigneFactureDTO> ligneFactures) {
        this.ligneFactures = ligneFactures;
    }

}
