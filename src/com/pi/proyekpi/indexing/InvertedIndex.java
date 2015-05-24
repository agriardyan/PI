/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pi.proyekpi.indexing;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Agustinus Agri <aagriard@gmail.com>
 */
public class InvertedIndex {

    private LinkedListOrderedUnique<Term> termList;

    public InvertedIndex() {
        termList = new LinkedListOrderedUnique<Term>();

    }

    public LinkedListOrderedUnique<Term> getTermList() {
        return termList;
    }

    /**
     * Menambahkan term pada objek termList
     * @param pTerm term yang akan ditambahkan
     * @param pDocument dokumen tempat term berada
     */
    public void add(String pTerm, String pDocument) {

        Term vTerm = termList.get(new Term(pTerm));

        if (vTerm == null) {
            Term term = new Term(pTerm);
            
            // nama dokumen ditambahkan dalam objek hashmap yang ada di kelas Term. Gunanya untuk word count
            term.addToHashMap(pDocument);
            
            term.postinglist = new LinkedListOrderedUnique<>();
            term.postinglist.addSort(new Document(pDocument)); 
            termList.add(term);
        } else {
            
            // nama dokumen ditambahkan dalam objek hashmap yang ada di kelas Term. Gunanya untuk word count
            vTerm.addToHashMap(pDocument);
            
            vTerm.getPostinglist().addSort(new Document(pDocument));
        }
    }

    /** 
     * Menampilkan inverted index pada seluruh dokumen yang ada
     */
    public void printInvertedIndexOnAllTerm() {
        ListIterator<Term> termListIterator = termList.listIterator();
        while (termListIterator.hasNext()) {
            Term term = termListIterator.next();
            System.out.print(term.getTerm() + " berada pada ");

            ListIterator<Document> postingListIterator = term.postinglist.listIterator();

            while (postingListIterator.hasNext()) {
                Document doc = postingListIterator.next();
                System.out.print(" " + term.getPostinglist().get(doc));
            }
            System.out.println("");
        }
    }

    /**
     * Menampilkan inverted index pada term spesifik (term yang diminta user)
     * @param pTerm term yang diminta user
     */
    public void printInvertedIndexOnSpecificTerm(String pTerm) {
        ListIterator<Term> termListIterator = termList.listIterator();
        while (termListIterator.hasNext()) {
            Term term = termListIterator.next();

            if (term.getTerm().equalsIgnoreCase(pTerm)) {
                System.out.print(term.getTerm() + " berada pada ");

                ListIterator<Document> postingListIterator = term.postinglist.listIterator();

                while (postingListIterator.hasNext()) {
                    Document doc = postingListIterator.next();
                    System.out.print(" " + term.getPostinglist().get(doc));
                }
            } else {
                System.out.println("Term " + term.getTerm() + " tidak ditemukan di dokumen manapun");
            }

        }
    }

    /**
     * Mencari dokumen yang memiliki konten berisi keyword yang dicari
     * @param cari keyword yang dicari
     * @return list nama-nama dokumen yang relevan
     * @throws NullPointerException 
     */
    public LinkedList<String> printSearch(String cari) throws NullPointerException {

        Term vTerm = termList.get(new Term(cari));
        if (vTerm == null) {
            return null;
        }

        LinkedList<String> list = new LinkedList<>();
        ListIterator<Document> iterator = vTerm.getPostinglist().listIterator();
        while (iterator.hasNext()) {
            Document vDocument = iterator.next();
            list.add(vDocument.getDocument());
        }

        return list;
    }

    /**
     * Menghilangkan duplikasi nama dokumen pada suatu list
     * @param list yang terdapat duplikasi
     * @return list yang bersih dari duplikasi
     */
    public static LinkedList<String> removeDuplicate(LinkedList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String currentItem = list.get(i);
            while (list.lastIndexOf(currentItem) != i) {
                list.removeLastOccurrence(currentItem);
            }
        }
        return list;
    }

    /**
     * Mencari duplikasi dalam list, untuk kemudian duplikasi tersebut disimpan dalam list lain
     * @param list list yang memiliki duplikasi
     * @return list berisi nama dokumen yang memiliki duplikat
     */
    public static LinkedList<String> findDuplicate(LinkedList<String> list) {
        LinkedList<String> li = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            String currentItem = list.get(i);
            if (list.lastIndexOf(currentItem) != i) {
                li.add(list.get(i));
            }
        }
        return li;
    }

    @Override
    public String toString() {
        return termList.toString();
    }
}
