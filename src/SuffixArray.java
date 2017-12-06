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
public class SuffixArray {
    private ArrayList<String> suffixArray;
    
    public ArrayList<String> getSuffixArray() {
        return suffixArray;
    }
    
    public SuffixArray(String string) {
        suffixArray = new ArrayList<>();
        
        // Add each suffix to the array.
        for (int i = 1; i <= string.length(); i++) {
            String suffix = string.substring(string.length() - i);
            suffixArray.add(suffix);
        }
        
        // Sort the suffixes.
        Collections.sort(suffixArray);
    }
}
