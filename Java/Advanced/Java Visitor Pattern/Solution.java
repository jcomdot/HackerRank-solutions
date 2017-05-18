package com.hackerrank.java;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

enum Color {
	RED, GREEN
}

abstract class Tree {

	private int value;
	private Color color;
	private int depth;

	public Tree(int value, Color color, int depth) {
		this.value = value;
		this.color = color;
		this.depth = depth;
	}

	public int getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public int getDepth() {
		return depth;
	}

	public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

	private ArrayList<Tree> children = new ArrayList<>();

	public TreeNode(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitNode(this);

		for (Tree child : children) {
			child.accept(visitor);
		}
	}

	public void addChild(Tree child) {
		children.add(child);
	}
}

class TreeLeaf extends Tree {

	public TreeLeaf(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitLeaf(this);
	}
}

abstract class TreeVis {
	public abstract int getResult();

	public abstract void visitNode(TreeNode node);

	public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
	
	private int sum = 0;
	
	public int getResult() {
		// implement this
		return this.sum;
	}

	public void visitNode(TreeNode node) {
	}

	public void visitLeaf(TreeLeaf leaf) {
		this.sum += leaf.getValue();
	}
}

class ProductOfRedNodesVisitor extends TreeVis {
	
	private BigInteger product = new BigInteger("1");
	
	public int getResult() {
		// implement this
		return product.remainder(BigInteger.valueOf((long) (Math.pow(10, 9)+7))).intValue();
	}

	public void visitNode(TreeNode node) {
		// implement this
		int value = node.getValue();
		value = value == 0 ? 1 : value;
		if (node.getColor() == Color.RED) product = product.multiply(BigInteger.valueOf(value));
	}

	public void visitLeaf(TreeLeaf leaf) {
		// implement this
		int value = leaf.getValue();
		value = value == 0 ? 1 : value;
		if (leaf.getColor() == Color.RED) product = product.multiply(BigInteger.valueOf(value));
	}
}

class FancyVisitor extends TreeVis {
	
	private int sumOfNonLeafEvenDepth = 0;
	private int sumOfGreenLeaf = 0;
	
	public int getResult() {
		// implement this
		return Math.abs(sumOfNonLeafEvenDepth - sumOfGreenLeaf);
	}

	public void visitNode(TreeNode node) {
		// implement this
		if (node.getDepth() % 2 == 0) this.sumOfNonLeafEvenDepth += node.getValue();
	}

	public void visitLeaf(TreeLeaf leaf) {
		// implement this
		if (leaf.getColor() == Color.GREEN) this.sumOfGreenLeaf += leaf.getValue();
	}
}

public class Solution {

	public static Tree solve() {
		//read the tree from STDIN and return its root as a return value of this function
        Scanner in = new Scanner(System.in);
        int cntNodes = in.nextInt();
        int[] values = new int[cntNodes];
        Color[] colors = new Color[cntNodes];
        int[] depths = new int[cntNodes];
        Tree[] nodes = new Tree[cntNodes];
        Set<Integer> parents = new HashSet<Integer>();
        int[][] aryNodes = new int[cntNodes-1][2];
        List<List<Integer>> listNodes = new ArrayList<List<Integer>>();
        // read in values
        for (int i=0; i<cntNodes; i++) {
            values[i] = in.nextInt();
        }
        // read in colors
        for (int i=0; i<cntNodes; i++) {
            colors[i] = in.nextInt() == 0 ? Color.RED : Color.GREEN;
        }
        // read in depths
        depths[0] = 0;
        for (int i=0; i<cntNodes-1; i++) {
            int parent = in.nextInt();
            int child = in.nextInt();
            aryNodes[i][0] = parent;
            aryNodes[i][1] = child;
            List<Integer> listNode = new ArrayList<>(2);
            listNode.add(parent);
            listNode.add(child);
            listNodes.add(listNode);
        }
        in.close();
        
        Set<Integer> seed = new HashSet<>();
        seed.add(1);
        int depth = 1;
        while (!seed.isEmpty()) {
        	Set<Integer> nextSeed = new HashSet<>();
        	Set<List<Integer>> removedNodes = new HashSet<>();
        	for (List<Integer> listNode : listNodes) {
            	if (seed.contains(listNode.get(0))) {
            		nextSeed.add(listNode.get(1));
            		depths[listNode.get(1)-1] = depth;
            		removedNodes.add(listNode);
            		parents.add(listNode.get(0));
            	}
            	if (seed.contains(listNode.get(1))) {
            		nextSeed.add(listNode.get(0));
            		depths[listNode.get(0)-1] = depth;
            		removedNodes.add(listNode);
            		parents.add(listNode.get(1));
            	}
        	}
    		listNodes.removeAll(removedNodes);
        	seed.clear();
        	seed.addAll(nextSeed);
    		if (listNodes.size() < 1)	break;
        	depth++;
        }
            
        for (int i=0; i<cntNodes-1; i++) {
        	if (nodes[aryNodes[i][0]-1] == null)
        		nodes[aryNodes[i][0]-1] = parents.contains(aryNodes[i][0]) ? new TreeNode(values[aryNodes[i][0]-1], colors[aryNodes[i][0]-1], depths[aryNodes[i][0]-1])
        			: new TreeLeaf(values[aryNodes[i][0]-1], colors[aryNodes[i][0]-1], depths[aryNodes[i][0]-1]); 
           	if (nodes[aryNodes[i][1]-1] == null)
            	nodes[aryNodes[i][1]-1] = parents.contains(aryNodes[i][1]) ? new TreeNode(values[aryNodes[i][1]-1], colors[aryNodes[i][1]-1], depths[aryNodes[i][1]-1])
        			: new TreeLeaf(values[aryNodes[i][1]-1], colors[aryNodes[i][1]-1], depths[aryNodes[i][1]-1]);

            if (depths[aryNodes[i][0]-1] < depths[aryNodes[i][1]-1]) {
            	((TreeNode)nodes[aryNodes[i][0]-1]).addChild(nodes[aryNodes[i][1]-1]);
            } else {
            	((TreeNode)nodes[aryNodes[i][1]-1]).addChild(nodes[aryNodes[i][0]-1]);
            }
        }
        
        Tree tree = nodes[0];
        
        return tree;
	}

	public static void main(String[] args) {
		Tree root = solve();
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
		ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
		FancyVisitor vis3 = new FancyVisitor();

		root.accept(vis1);
		root.accept(vis2);
		root.accept(vis3);

		int res1 = vis1.getResult();
		int res2 = vis2.getResult();
		int res3 = vis3.getResult();

		System.out.println(res1);
		System.out.println(res2);
		System.out.println(res3);
	}

}// end of Solution
