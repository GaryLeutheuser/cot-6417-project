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
public class WaveletTreeNode {
    private String string;
    
    private int[] bitVector;
    public int[][] smallRank;
    public int[] boundaryRank;
    
    private int n;
    public int t;
    private int numBlocks;
    
    WaveletTreeNode leftChild;
    WaveletTreeNode rightChild;
    
    public String getString() {
        return string;
    }
    
    public int[] getbitVector() {
        return bitVector;
    }
    
    WaveletTreeNode(String string) {
        this.string = string;
        
        // Determine the alphabet of the string.
        HashSet<Character> alphabet = new HashSet<>();
        for (int i = 0; i < string.length(); i++) {
            alphabet.add(string.charAt(i));
        }
        
        // Move the characters in the alphabet to a sortable data type.
        ArrayList<Character> alphabetArray = new ArrayList<>();
        for (Character c : alphabet)
            alphabetArray.add(c);
            
        // Sort the alphabet.
        Collections.sort(alphabetArray);
        
        // Assign 0/1 values to the characters.
        Hashtable<Character, Integer> partition = new Hashtable<>();
        for (int i = 0; i < alphabetArray.size(); i++) {
            // Get the current character of the alphabet.
            char c = alphabetArray.get(i);
            
            // Don't add a character multiple times.
            if (partition.containsKey(c))
                continue;
            
            // Assign the value.
            if (i < (alphabetArray.size() / 2))
                partition.put(c, 0);
            else
                partition.put(c, 1);
        }
        
        // Create the bitvector using the map created previously.
        bitVector = new int[string.length()];
        for (int i = 0; i < string.length(); i++) {
            int bit = partition.get(string.charAt(i));
            bitVector[i] = bit;
        }
        
        // Establish some helpful numbers.
        n = bitVector.length;
        t = (int) (Math.log(n)/2);
        if (t == 0)
            t = 1;
        numBlocks = (int) Math.ceil((double) n / (double) t);
        
        // Create the smallRank LUT.
        smallRank = new int[(int) (Math.pow(2, t))][t];
        for (int i = 0; i < smallRank.length; i++) {
            // Create the next possible bit vector
            String B = Integer.toBinaryString(i);
            // Add leading zeroes
            while (B.length() < smallRank[0].length) {
               String zero = new String("0");
               B = zero.concat(B);
            }
            // Iterate over the bitvector computing the rank values
            for (int j = 0; j < B.length(); j++) {
                if (j == 0) {
                    if (B.charAt(j) == '0')
                        smallRank[i][j] = 0;
                    else
                        smallRank[i][j] = 1;
                }
                else {
                    if (B.charAt(j) == '0')
                        smallRank[i][j] = smallRank[i][j-1];
                    else
                        smallRank[i][j] = smallRank[i][j-1] + 1;
                }
            }
        }
        
        // Create the boundaryRank LUT.
        // BoundaryRank stores the maximum number of times that the bit 1
        // occurs in blocks preceding the given number.
        boundaryRank = new int[numBlocks];
        boundaryRank[0] = 0;
        for (int i = 1; i < boundaryRank.length; i++) {
            // Compute the number of 1's in the previous block
            int numOnesInBlock = 0;
            for (int j = 0; j < t; j++) {
                if (bitVector[(i*t) - t + j] == 1)
                    numOnesInBlock++;
            }
            // Number of 1's in blocks thus far is number just seen plus previous
            boundaryRank[i] = numOnesInBlock + boundaryRank[i-1];
        }
        
        // Create the children of this node.
        if (alphabetArray.size() <= 1) {
            leftChild = null;
            rightChild = null;
            return;
        }
        
        // Create left/right strings based on the bit vector.
        StringBuilder leftString = new StringBuilder();
        StringBuilder rightString = new StringBuilder();
        
        for (int i = 0; i < bitVector.length; i++) {
            if (bitVector[i] == 0)
                leftString.append(string.charAt(i));
            else
                rightString.append(string.charAt(i));
        }
        
        // Create the child nodes.
        leftChild = new WaveletTreeNode(leftString.toString());
        rightChild = new WaveletTreeNode(rightString.toString());
    }
    
    // Return the rank 1 for the given bit vector and length.
    public int rankOne(int i) {
        int rank = 0;
        for (int index = 0; index < i; index++) {
            if (bitVector[index] == 1) {
                rank++;
            }
        }
        
        return rank;
    }
    
    public int fastRankOne(int i) {
        // Index from zero.
        i = i - 1;
        
        // Determine the block.
        int j = i / t;
        
        // Determine the index within the block.
        int k = i % t;
        
        // Determine the numerical representation of the block.
        int y = 0;
        for (int bit = 0; bit < t && (j*t) + bit < bitVector.length; bit++) {
            // Gracefully fall of the end of the array.
            if ((j*t) + bit > bitVector.length - 1)
                break;
            // Perform normal computation while in the array.
            else
                y += bitVector[(j*t) + bit] * (int) (Math.pow(2, t - 1 - bit));
        }     
        
        return this.boundaryRank[j] + this.smallRank[y][k];
    }
}
