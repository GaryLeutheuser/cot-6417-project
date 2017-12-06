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
public class BWT {
    private String transformed;
    
    public BWT(String string) {
        transformed = transform(string);
    }
    
    public String getBWT() {
        return transformed;
    }
    
    private String transform(String string) {
        // Establish an array of strings
        ArrayList<String> rotations = new ArrayList<>();
        
        // Add every rotation to the matrix
        for (int i = 0; i < string.length(); i++) {
            rotations.add(string);
            string = rotateRight(string);
        }
        
        // Sort the strings
        Collections.sort(rotations);
        
        // The BWT is the last character of each string combined.
        StringBuilder transformedString = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            transformedString.append(rotations.get(i).charAt(string.length() - 1));
        }
        
        // Add input to the array of strings
        return transformedString.toString();
    }
    
    private String rotateRight(String string) {
        StringBuilder rotatedStrBuilder = new StringBuilder();
        rotatedStrBuilder.append(string.substring(string.length() - 1));
        rotatedStrBuilder.append(string.substring(0, string.length() - 1));
        return rotatedStrBuilder.toString();
    }
}
