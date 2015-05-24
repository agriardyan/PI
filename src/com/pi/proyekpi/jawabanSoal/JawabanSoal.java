/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pi.proyekpi.jawabanSoal;

import com.pi.proyekpi.processor.Processor;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author basisc08
 */
public class JawabanSoal {
    
    private Scanner sc;
    private Processor ps;

    public JawabanSoal() {
        sc = new Scanner(System.in);
        ps = new Processor();
    }
    
    /**
     * Menampilkan isi dokumen
     */
    public void soalNomor1() {
        
        // bagian ini menampilkan seluruh isi dokumen
        try {
            ps.showAllDocumentContent();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JawabanSoal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("");
        
        // bagian ini menampilkan dokumen yang diminta saja
                
        String[] fileList = ps.documentFileList();
        for (int i = 0; i < fileList.length; i++) {
            System.out.printf("%s. %s %s", i, fileList[i], "\n");
        }
        
        System.out.print("Pilih dokumen (masukkan angka) : ");
        
        ps.showDocumentContentOnSpecificDocument(ps.documentFileList()[sc.nextInt()]);
        
    }
    
    /**
     * Menampilkan search result
     */
    public void soalNomor2() {
        
        // Menampilkan hasil searching dari 1 keyword
        System.out.print("Masukkan keyword 1 kata : ");
        ps.searchOneKeyword(sc.next());
        
        
        
        
        // Menampilkan hasil searching dari 2 keyword
        System.out.print("Masukkan keyword 2 kata : ");
        String[] keywordArray = new String[2];
        keywordArray[0] = sc.next();
        keywordArray[1] = sc.next();
        
        System.out.println("");
        
        // User memilih menggunakan AND atau OR
        System.out.printf("%s. %s %s", Processor.LogicalOperator.AND.ordinal(), Processor.LogicalOperator.AND.toString(), "\n");
        System.out.printf("%s. %s %s", (int) Processor.LogicalOperator.OR.ordinal(), Processor.LogicalOperator.OR.toString(), "\n");
        System.out.print("Pilih operator logikal : ");
        
        // Eksekusi
        if(sc.nextInt() == 0) {
            ps.searchMultipleKeyword(Processor.LogicalOperator.AND, keywordArray[0], keywordArray[1]);
        } else {
            ps.searchMultipleKeyword(Processor.LogicalOperator.OR, keywordArray[0], keywordArray[1]);
        }
    }
    
    /**
     * Menampilkan word (term) count
     */
    public void soalNomor3() {
        // Term count dari seluruh dokumen
        System.out.println("Term count dari seluruh dokumen : ");
        ps.showWordCountOnAllDocument();
        
        // Term count dari term yang diminta user
        System.out.println("Term count dari term spesifik : ");
        ps.showWordCountOnSpecificTerm(sc.next());
    }

}
