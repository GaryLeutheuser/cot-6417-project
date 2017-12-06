/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author 8647
 */
public class Test {
    public static void main(String[] args) {
        String text = "I came across this rather unexpected problem. I have a gtk.TreeView with a single text column, that is rendered by gtk.CellRendererText. What I want is that the user can mark the displayed text using the mouse and get it into the clipboard by pressing ctrl+c. (I am referring to the very basic feature present in every webbrowser and texteditor). However, gtk won't let me do it. I have a simple example here, with non-markable / non-highlightable text:";
        String pattern = "the";
        
        // Preprocessing.
        BWT         L   = new BWT(text);
        SuffixArray SA  = new SuffixArray(text);
        WaveletTree WT  = new WaveletTree(L.getBWT());
        
        // Obtain the alphabet of the text.
        HashSet<Character> textAlphabet = new HashSet<>();
        for (int i = 0; i < text.length(); i++) {
            textAlphabet.add(text.charAt(i));
        }
        
        // Compute the C mapping.
        Hashtable<Character, Integer> C = new Hashtable<>();
        for (Character c : textAlphabet) {
            // Add the character if it isn't already present.
            if (!C.contains(c))
                C.put(c, 0);
            
            // Iterate over the text, computing C.
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) < c) {
                    C.put(c, C.get(c) + 1);
                }
            }
        }
        
        // All values are prepared for the search.
        int i = pattern.length();
        int sp = 1;
        int ep = L.getBWT().length();
        while (sp <= ep && i >= 1) {
            char c = pattern.charAt(i-1);
            sp = C.get(c) + WT.occ(WT.getRoot(), sp - 1, c) + 1;
            ep = C.get(c) + WT.occ(WT.getRoot(), ep, c);
            i--;
        }
        if (ep < sp)
            System.out.println("Pattern not found.");
        else
            System.out.println("<sp, ep> pair is <" + sp + ", " + ep + ">");
        
        for (int j = sp; j <= ep; j++) {
            System.out.println(SA.getSuffixArray().get(j-1).toString());
        }
    }
}
