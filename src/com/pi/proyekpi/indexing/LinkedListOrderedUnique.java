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
public class LinkedListOrderedUnique<T> extends LinkedList<T> {

    int tf = 1;

    public boolean addSort(T e) {
        ListIterator<T> iterator = this.listIterator();
        while (iterator.hasNext()) {
            Document dok = new Document();
            T temp = iterator.next();
            if (((Comparable) temp).compareTo(e) > 0) {
                iterator.previous();
                iterator.add(e);

                tf = 1;
                dok.setDf(tf);

                return true;
            } else if (((Comparable) temp).compareTo(e) == 0) { 
                
                tf = tf + 1;
                dok.setDf(tf);

                return true;
            }
        }
        iterator.add(e);
        return true;
    }

    public T get(T e) {
        ListIterator<T> iterator = this.listIterator();
        while (iterator.hasNext()) {
            T temp = iterator.next();
            if (((Comparable) temp).compareTo(e) == 0) { 
                return temp;
            }
        }
        return null;
    }

    public String toString() {
        String temp = "";
        ListIterator<T> iterator = this.listIterator();
        while (iterator.hasNext()) {
            temp = temp + iterator.next() + "\n ";
        }
        return temp;
    }
}
