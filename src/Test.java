/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.*;

/**
 *
 * @author 8647
 */
public class Test {
    public static void main(String[] args) {
        // Read in filename from user
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter the filename to be used as the text:");
        String file = inputScanner.nextLine();
        
        // Read in text from file
        String text = new String();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            text = sb.toString();
        }
        catch (Exception e) {
            System.out.println(e.toString());
            System.exit(-1);
        }
        
        // Preprocessing.
        long startTime = System.nanoTime();
        BWT         L   = new BWT(text);
        SuffixArray SA  = new SuffixArray(text);
        WaveletTree WT  = new WaveletTree(L.getBWT());
        
        // Obtain the alphabet of the text.
        HashSet<Character> textAlphabet = new HashSet<>();
        for (int i = 0; i < text.length(); i++) {
            textAlphabet.add(text.charAt(i));
        }
        
        System.out.println("The size of the alphabet of the text is " + textAlphabet.size());
        
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
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        
        System.out.println("Construction time: " + duration/1000000);
        
        // Search for patterns forever.
        Scanner input = new Scanner(System.in);
        String pattern = null;
        while (true) {
            System.out.println();
            System.out.println("Enter a pattern:");
            pattern = input.nextLine();

            // Run the algorithm a hundred times to get a reasonable time value.
            startTime = System.nanoTime();
            int sp = 0;
            int ep = 0;
            for (int repeat = 0; repeat < 1000; repeat++) {
                // All values are prepared for the search.
                int i = pattern.length();
                sp = 1;
                ep = L.getBWT().length();
                while (sp <= ep && i >= 1) {
                    char c = pattern.charAt(i-1);
                    sp = C.get(c) + WT.occ(WT.getRoot(), sp - 1, c) + 1;
                    ep = C.get(c) + WT.occ(WT.getRoot(), ep, c);
                    i--;
                }
            }
            
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            
            if (ep < sp)
                System.out.println("Pattern not found.");
            else
                System.out.println("<sp, ep> pair is <" + sp + ", " + ep + ">");

//            for (int j = sp; j <= ep; j++) {
//                System.out.println(SA.getSuffixArray().get(j-1).toString());
//            }
            
            System.out.println();
            System.out.println("The text length is " + text.length());
            System.out.println("The pattern length is " + pattern.length());
            System.out.println("1000 repetitions of the search took " + duration/1000000 + " milliseconds.");
        }
    }
}
