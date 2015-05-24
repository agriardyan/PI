/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pi.proyekpi.manipulator;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Agustinus Agri <aagriard@gmail.com>
 */
public class ListManipulator {
    
    /**
     * Digunakan untuk merubah list ke String. Hasilnya berbeda dengan method toString()
     * @param ls list yang akan diubah ke bentuk String
     * @return result - hasil pengubahan
     */
    public static String listToString(LinkedList<String> ls) {
        Iterator<String> iterator = ls.iterator();
        String result = "";
        while(iterator.hasNext()) {
            result = result + iterator.next() + " ";
        }
        
        return result;
    }
    
}
