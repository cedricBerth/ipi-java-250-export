package com.example.demo.service.export;

import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;
import com.example.demo.entity.Facture;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportPDFITextService {

    public void export(OutputStream os, FactureDTO facture) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, os);
        document.open();
        
        Paragraph numFacture = new Paragraph("Numéro de facture : " + facture.getId());
        Paragraph nomClient = new Paragraph(new Paragraph("Nom client : " + facture.getClient().getNom()));
        Paragraph prenomClient = new Paragraph("Prénom client : " + facture.getClient().getPrenom() + " \n");
        

        // Tableau de tant de colonnes 
        List<Phrase> phrases = new ArrayList<>(); 
        phrases.add(new Phrase("Désignation"));
        phrases.add(new Phrase("Qté"));
        phrases.add(new Phrase("PU"));
        
        int nbChamps = 0;
        PdfPTable pdfTable = new PdfPTable(3);   
        // On parcourt le tableau des header
        for (int index = 0; index < phrases.size(); index++) {     
            // On a le numéro de facture, le nom et le prénom
            PdfPCell cellHeader = new PdfPCell();
            cellHeader.setPhrase(new Phrase(phrases.get(index)));
            pdfTable.addCell(cellHeader);
        }
        
        // Initialiser la valeur qui récupère le total de la facture 
        Double total = 0d; 
        // On récupère la liste des lignes facture dans une liste 
        List<LigneFactureDTO> lignesFacture = facture.getLigneFactures(); 
        for (LigneFactureDTO ligneFacture : lignesFacture) {
        	pdfTable.addCell(ligneFacture.getDesignation());
        	pdfTable.addCell(ligneFacture.getQuantite().toString());
        	pdfTable.addCell(ligneFacture.getPrixUnitaire().toString());
            total += ligneFacture.getPrixUnitaire() * ligneFacture.getQuantite(); 
        }
       
       // Ligne du total 
       PdfPTable pdfTable2 = new PdfPTable(3); 
       pdfTable2.addCell("");
       pdfTable2.addCell("Total");
       pdfTable2.addCell(total.toString());
        
        
        document.add(numFacture);
        document.add(nomClient); 
        document.add(prenomClient); 
        document.add(pdfTable); 
        document.add(pdfTable2);
        document.close();
    }
}
