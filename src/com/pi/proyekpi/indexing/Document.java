/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pi.proyekpi.indexing;

/**
 *
 * @author Agustinus Agri <aagriard@gmail.com>
 */
public class Document implements Comparable {

    protected String document;
    private int df;

    public Document(){

    }

    public Document(String pDocument) {
        document = pDocument;
    }

    public String getDocument() {
        return document;
    }

    public int compareTo(Object o) {
        return document.compareTo(((Document) o).getDocument());
    }

    public String toString() {
        return document;
    }

    /**
     * @return the df
     */
    public int getDf() {
        return df;
    }

    /**
     * @param df the df to set
     */
    public void setDf(int df) {
        this.df = df;
    }

}
