
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 8647
 */
public class WaveletTree {
    private WaveletTreeNode root;
    
    public WaveletTreeNode getRoot() {
        return root;
    }
    
    public WaveletTree(String string) {
        // Recursively create wavelet tree nodes.
        root = new WaveletTreeNode(string);
    }
    
    public int occ(WaveletTreeNode node, int i, char c) {
        // Base cases
        if (i == 0)
            return 0;
        if (node.leftChild == null && node.rightChild == null)
            return i;
        
        // The character is in the right child.
        if (node.rightChild.getString().contains(Character.toString(c))) {
            int x = node.fastRankOne(i);
            return occ(node.rightChild, x, c);
        }
        // The character is the left child.
        else {
            int x = i - node.fastRankOne(i); 
            return occ(node.leftChild, x, c);
        }
    }
}
