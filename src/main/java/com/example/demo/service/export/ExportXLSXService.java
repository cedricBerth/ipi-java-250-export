package com.example.demo.service.export;

import com.example.demo.dto.ClientDTO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
}
