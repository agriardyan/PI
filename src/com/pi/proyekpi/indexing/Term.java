/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pi.proyekpi.indexing;

import java.util.HashMap;

/**
 *
 * @author Agustinus Agri <aagriard@gmail.com>
 */
public class Term implements Comparable {

    private String term;
    protected LinkedListOrderedUnique<Document> postinglist; 
    private HashMap<String, Integer> termHashMap;

    public Term(){

    }

    public Term(String pTerm) {
        term = pTerm;
        termHashMap = new HashMap<>();
    }

    public String getTerm() {
        return term;
    }

    public LinkedListOrderedUnique<Document> getPostinglist() {
        return postinglist;
    }
    
    public void addToHashMap(String pDocument) {
        if(termHashMap.get(pDocument) == null || termHashMap.get(pDocument) == 0) {
            termHashMap.put(pDocument, 1);
        } else {
            int wordCount = termHashMap.get(pDocument);
            termHashMap.remove(pDocument);
            termHashMap.put(pDocument, wordCount + 1);
        }        
    }
    
    public HashMap getTermHashMap() {
        return termHashMap;
    }

    public int compareTo(Object o) {
        return term.compareTo(((Term) o).getTerm());//casting karena tipenya o nya Object yaitu kelas Term
    }

    @Override
    public String toString() {
        String temp = term + " => ";
        temp = temp + postinglist.toString();
        return temp;
    }
}
