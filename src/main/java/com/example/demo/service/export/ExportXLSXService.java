package com.example.demo.service.export;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

@Service
public class ExportXLSXService {
	

    public void export(OutputStream os, List<ClientDTO> clients) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("clients");
        
        // Initialisation de la ligne à 0
        int rowNum = 0; 
        // Ligne et cellules du header initialisés
        Row rowHeader = sheet.createRow(rowNum);
        Cell cellNomHeader = rowHeader.createCell(0); 
        Cell cellPrenomHeader = rowHeader.createCell(1); 
        cellNomHeader.setCellValue("Nom");
        cellPrenomHeader.setCellValue("Prénom");
        List<Cell> cellsBody = new ArrayList<>(); 
        cellsBody.add(cellPrenomHeader); 
        cellsBody.add(cellNomHeader); 
        
        rowNum++; 
        
        // Parcours de tous les clients
        for (ClientDTO clientDTO : clients) {
        	// Création d'une nouvelle ligne
        	Row rowBody = sheet.createRow(rowNum); 
        	// Parcours du tableau jusqu'à la fin des éléments
        	for (int index = 0; index < cellsBody.size(); index++) {
        		Cell cell = rowBody.createCell(index);
        		if (index == 0) {
        			cell.setCellValue(clientDTO.getNom());
        		}
        		
        		if (index == 1) {
        			cell.setCellValue(clientDTO.getPrenom());
        		}
        	}
        	rowNum++; 
        }

        workbook.write(os);
        workbook.close();
    }
    
    // permet de d'exporter les factures en fonction d'un client
	public void exportFactures(OutputStream os, List<FactureDTO> factures, Long clientId) throws IOException {
		
        XSSFWorkbook workbook = new XSSFWorkbook();
        

        // Boucle : Parcours de tous les factures
        for (FactureDTO facture : factures) {
        	// Tu vérifies si l'id du client de facture correspond à l'id du client
        	if (facture.getClient().getId() == clientId) {
    	        // Création d'un onglet à chaque tour de boucle
                XSSFSheet sheet = workbook.createSheet("Facture " + facture.getId().toString());
                
                // Total global
                Double totalGlobal = 0d; 
                Double totalLigne = 0d; 
            	// Création des header pour chaque onglet
            	int rowNum = 0;
    	        Row rowHeader = sheet.createRow(rowNum);
    	        Cell cellDesignationHeader = rowHeader.createCell(0); 
    	        Cell cellQteHeader = rowHeader.createCell(1);
    	        Cell cellPuHeader = rowHeader.createCell(2);
    	        Cell cellPrixLigneHeader = rowHeader.createCell(3);
    	        cellDesignationHeader.setCellValue("Designation");
    	        cellPuHeader.setCellValue("Quantité");
    	        cellQteHeader.setCellValue("PU");
    	        cellPrixLigneHeader.setCellValue("Sous-total");
    	        List<Cell> cellsBody = new ArrayList<>(); 
    	        cellsBody.add(cellDesignationHeader); 
    	        cellsBody.add(cellQteHeader); 
    	        cellsBody.add(cellPuHeader); 
    	        cellsBody.add(cellPrixLigneHeader); 
    	        rowNum++;
    	        
    	        // Tu récupères la liste des lignes facture pour chaque facture
    			List<LigneFactureDTO> lignesFacture = facture.getLigneFactures(); 
    	        
                // Pour chaque facture, on récupère la liste des lignes factures
            	for (LigneFactureDTO ligneFacture : lignesFacture) {
                    // Création d'une ligne 
            		Row rowBody = sheet.createRow(rowNum++); 
            		// Boucle sur les cellules requises
            		for (int index = 0; index < cellsBody.size(); index++) {
            			// Calcul du total par ligne
            			totalLigne = ligneFacture.getPrixUnitaire() * ligneFacture.getQuantite(); 
                        // Création des cellules
            			Cell cells = rowBody.createCell(index); 

            			// selon l'index, on récupère la valeur de ligneFacture
            			if (index == 0) {
            				cells.setCellValue(ligneFacture.getDesignation());
            			}
            			
            			if (index == 1) {
            				cells.setCellValue(ligneFacture.getQuantite());
            			}
            			
            			if (index == 2) {
            				cells.setCellValue(ligneFacture.getPrixUnitaire());
            			}
            			
            			if (index == 3) {
            				cells.setCellValue(totalLigne);
            			}
            		}
                	totalGlobal += totalLigne; 

            	}
            	
            	// Création d'une nouvelle ligne pour le total
            	Row rowTotal = sheet.createRow(rowNum++); 
            	Cell cellTotalLibelle = rowTotal.createCell(2);
            	Cell cellTotalValue = rowTotal.createCell(3);
            	
            	cellTotalLibelle.setCellValue("Total");
            	cellTotalValue.setCellValue(String.valueOf(totalGlobal));
            	
        	}
        }
        workbook.write(os);
        workbook.close();
	}
}
