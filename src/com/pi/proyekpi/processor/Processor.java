/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pi.proyekpi.processor;

import com.pi.proyekpi.indexing.InvertedIndex;
import com.pi.proyekpi.indexing.LinkedListOrderedUnique;
import com.pi.proyekpi.indexing.Term;
import com.pi.proyekpi.manipulator.ListManipulator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Agri <aagriard@gmail.com>
 */
public class Processor {

    private InvertedIndex index;

    private Scanner sc;

    private final File DOCUMENT_FILES_DIRECTORY = new File("." + File.separator + "Koleksi");

    private final String INCLUDED_CHARACTER = "[^a-zA-Z0-9]";

    public enum LogicalOperator {

        AND,
        OR
    }

    /**
     *
     */
    public Processor() {
        index = new InvertedIndex();
        try {
            inputDocumentFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] documentFileList() {
        return DOCUMENT_FILES_DIRECTORY.list();
    }

    private void inputDocumentFile() throws FileNotFoundException {
        String[] fileNameList = documentFileList();

        for (String fileName : fileNameList) {
            sc = new Scanner(new File(DOCUMENT_FILES_DIRECTORY.getAbsolutePath() + File.separator + fileName));

            while (sc.hasNext()) {
                StringTokenizer st = new StringTokenizer(sc.nextLine());
                while (st.hasMoreTokens()) {
                    String cleanToken = st.nextToken().replaceAll(INCLUDED_CHARACTER, "");
                    index.add(cleanToken, fileName);
                }
            }
        }
    }

    public void showAllDocumentContent() throws FileNotFoundException {
        String[] fileNameList = documentFileList();

        for (String fileName : fileNameList) {
            showDocumentContent(fileName);
        }
    }

    public void showDocumentContentOnSpecificDocument(String pFileName) {
        try {
            showDocumentContent(pFileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showDocumentContent(String pFileName) throws FileNotFoundException {

        System.out.println("\n############################");
        System.out.println("\nIsi dari file " + pFileName + " : ");
        System.out.println("\n############################");

        sc = new Scanner(new File(DOCUMENT_FILES_DIRECTORY.getAbsolutePath() + File.separator + pFileName));

        while (sc.hasNext()) {
            System.out.println(sc.nextLine());
        }
    }

    public void searchOneKeyword(String pSearchKeyword) {
        System.out.print("Masukan 1 kata : ");

        LinkedList<String> containedDocumentName = index.printSearch(pSearchKeyword);
        if (containedDocumentName != null) {
            System.out.println("Kata tersebut terdapat di ");
            System.out.println(ListManipulator.listToString(containedDocumentName));
        } else {
            System.out.println("Kata tersebut tidak ditemukan di dokumen manapun ");
        }

    }

    public void searchMultipleKeyword(LogicalOperator pOperator, String pSearchKeyword1, String pSearchKeyword2) {

        String searchKeyword = pSearchKeyword1 + " " + pSearchKeyword2;
        LinkedList list = new LinkedList();

        StringTokenizer searchKeywordTokenizer = new StringTokenizer(searchKeyword);

        LinkedList<String> containedDocumentNameList = index.printSearch(searchKeywordTokenizer.nextToken());

        if (emptyChecker(containedDocumentNameList)) {
            System.out.println(" Kata tersebut tidak ditemukan di dokumen manapun");
            return;
        }

        String containedDocumentName = ListManipulator.listToString(containedDocumentNameList);

        StringTokenizer containedDocumentTokenizer = new StringTokenizer(containedDocumentName);

        while (containedDocumentTokenizer.hasMoreTokens()) {
            list.add(containedDocumentTokenizer.nextToken());
        }

        while (searchKeywordTokenizer.hasMoreTokens()) {
            containedDocumentNameList = index.printSearch(searchKeywordTokenizer.nextToken());
            if (emptyChecker(containedDocumentNameList)) {
                System.out.println(" Kata tersebut tidak ditemukan di dokumen manapun");
                return;
            }
            containedDocumentTokenizer = new StringTokenizer(ListManipulator.listToString(containedDocumentNameList));
            while (containedDocumentTokenizer.hasMoreTokens()) {
                list.add(containedDocumentTokenizer.nextToken());
            }
        }

        if (pOperator == LogicalOperator.OR) {
            LinkedList removedDuplicateList = InvertedIndex.removeDuplicate(list);
            System.out.print(ListManipulator.listToString(removedDuplicateList));
        } else {
            LinkedList findDuplicateList = InvertedIndex.findDuplicate(list);
            System.out.print(ListManipulator.listToString(findDuplicateList));
        }
    }

    public void showWordCountOnAllDocument() {
        LinkedListOrderedUnique<Term> termList = index.getTermList();
        Iterator<Term> iterator = termList.iterator();
        while (iterator.hasNext()) {
            Term term = iterator.next();
            Map<String, Integer> map = term.getTermHashMap();
            List<String> keyList = new ArrayList<>(map.keySet());

            for (String key : keyList) {
                System.out.println(key + " mengandung term " + term.getTerm().toUpperCase() + " sejumlah " + map.get(key));
            }

        }
    }

    public void showWordCountOnSpecificTerm(String pTerm) {
        LinkedListOrderedUnique<Term> termList = index.getTermList();
        Iterator<Term> iterator = termList.iterator();
        while (iterator.hasNext()) {
            Term term = iterator.next();
            if (term.getTerm().equalsIgnoreCase(pTerm)) {
                Map<String, Integer> map = term.getTermHashMap();
                List<String> keyList = new ArrayList<>(map.keySet());

                for (String key : keyList) {
                    System.out.println(key + " mengandung term " + term.getTerm().toUpperCase() + " sejumlah " + map.get(key));
                }
                return;
            }
        }

        System.out.println("Tidak ditemukan term tersebut di dokumen manapun");
    }

    /**
     * Method checking dari list yang diberikan apakah kosong atau tidak
     *
     * @param list
     * @return
     */
    public boolean emptyChecker(LinkedList<String> list) {
        return list == null;
    }

}
