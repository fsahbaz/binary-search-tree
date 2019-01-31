package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {
  
  /*
   * 
   * YOUR CODE HERE
   * 
   */

	protected BinaryTreeNode<Key, Value> parent;
	protected BinaryTreeNode<Key, Value> leftChild;
	protected BinaryTreeNode<Key, Value> rightChild;
	
	public BinaryTreeNode(Key k, Value v, BinaryTreeNode<Key, Value> parent) {
		super(k,v);
		this.parent = parent;
		
	}

	public BinaryTreeNode<Key, Value> getParent() {
		return parent;
	}

	public void setParent(BinaryTreeNode<Key, Value> parent) {
		this.parent = parent;
	}

	public BinaryTreeNode<Key, Value> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(BinaryTreeNode<Key, Value> leftChild) {
		this.leftChild = leftChild;
	}

	public BinaryTreeNode<Key, Value> getRightChild() {
		return rightChild;
	}

	public void setRightChild(BinaryTreeNode<Key, Value> rightChild) {
		this.rightChild = rightChild;
	}
	
	
}
