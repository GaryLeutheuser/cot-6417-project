
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
            if (node.rankOne(i) != node.fastRankOne(i)) {
//                System.out.println("Bit vector: " + Arrays.toString(node.getbitVector()));
//                System.out.println("Smallrank: " + Arrays.toString(node.smallRank));
                System.out.println("i: " + i);
                System.out.println("Block size: " + node.t);
//                System.out.println("Boundaryrank: " + Arrays.toString(node.boundaryRank));
                System.out.println("Correct rank = " + node.rankOne(i));
                System.out.println("Fast rank = " + node.fastRankOne(i));
                System.out.println();
            }
            int x = node.fastRankOne(i);
            return occ(node.rightChild, x, c);
        }
        // The character is the left child.
        else {
            if (node.rankOne(i) != node.fastRankOne(i)) {
//                System.out.println("Bit vector: " + Arrays.toString(node.getbitVector()));
//                System.out.println("Smallrank: " + Arrays.toString(node.smallRank));
                System.out.println("i: " + i);
                System.out.println("Block size: " + node.t);
//                System.out.println("Boundaryrank: " + Arrays.toString(node.boundaryRank));
                System.out.println("Correct rank = " + node.rankOne(i));
                System.out.println("Fast rank = " + node.fastRankOne(i));
                System.out.println();
            }
            int x = i - node.fastRankOne(i); 
            return occ(node.leftChild, x, c);
        }
    }
}
