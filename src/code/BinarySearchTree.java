package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import given.iMap;
import given.DefaultComparator;
import given.Entry;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key , Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	protected BinaryTreeNode<Key, Value> root = null;
	private Comparator<Key> comparator;
	private int size = 0;
	
	public BinarySearchTree() { }
	
	protected BinaryTreeNode<Key, Value> validate(BinaryTreeNode<Key, Value> node) {
		if(node.parent == node) {
			throw new IllegalArgumentException("This node is no longer in the tree");
		}
		return node;
	}
	
	
	protected int height(BinaryTreeNode<Key, Value> node) {
		if(isExternal(node)) return 0;
		return 1 + Math.max(height(node.leftChild), height(node.rightChild));
	}
	
	public BinaryTreeNode<Key, Value> addRoot(Key k, Value v) {
		if(isEmpty()) {
			root = new BinaryTreeNode<Key, Value>(k, v, null);
			root.setLeftChild(null);
			root.setRightChild(null);
			return root;
		}
		throw new IllegalStateException("Tree is not empty");
	}
	
	public BinaryTreeNode<Key, Value> treeSearch(BinaryTreeNode<Key, Value> node, Key key) {
		if(isExternal(node)) return node;
		if(this.comparator.compare(key, node.getKey()) == 0) {
			return node;
		} else if(this.comparator.compare(key, node.getKey()) < 0) {
			return treeSearch(node.getLeftChild(), key);
		} else {
			return treeSearch(node.getRightChild(), key);
		}
	}
	
	@Override
	public Value get(Key k) {
		return getValue(k);
	}

	@Override
	public Value put(Key k, Value v) {
		if(root == null) {
			addRoot(k,v);
		}
		BinaryTreeNode<Key, Value> node = treeSearch(root,k);
		if(isExternal(node)) {
			BinaryTreeNode<Key, Value> c1 = new BinaryTreeNode<Key, Value>(null, null, node);
			BinaryTreeNode<Key, Value> c2 = new BinaryTreeNode<Key, Value>(null, null, node);
			node.setKey(k);
			node.setValue(v);
			node.setLeftChild(c1);
			node.setRightChild(c2);
			size++;
			return null;
		}
		Value val = node.getValue();
		node.setValue(v);
		return val;
		
	}

	@Override
	public Value remove(Key k) {
		BinaryTreeNode<Key, Value> node = treeSearch(root, k);
		if(isExternal(node) || isEmpty()) {
			return null;
		} else {
			Value val2BeRet = node.getValue();
			if(isExternal(node.getLeftChild()) && isExternal(node.getRightChild())) {
				if(isRoot(node)) {
					root = node.getLeftChild();
					root.setParent(null);
					node.setParent(node);
					node.setLeftChild(null);
					node.setRightChild(null);
				} else {
					if(isRightChild(node)) {
						node.getParent().setRightChild(node.getLeftChild());
					} else {
						node.getParent().setLeftChild(node.getLeftChild());
					}
					node.getLeftChild().setParent(node.getParent());
					node.setParent(node);
				}
			} else if(!isExternal(node.getLeftChild()) && isExternal(node.getRightChild())) {
				if(isRoot(node)) {
					root = node.getLeftChild();
					root.setParent(null);
					node.setParent(node);
					node.setLeftChild(null);
					node.setRightChild(null);
				} else {
					node.getLeftChild().setParent(node.getParent());
					if(isLeftChild(node)) {
						node.getParent().setLeftChild(node.getLeftChild());
					} else {
						node.getParent().setRightChild(node.getLeftChild());
					}
					node.setParent(node);
					node.setLeftChild(null);
					node.setRightChild(null);
				}
			} else if(isExternal(node.getLeftChild()) && !isExternal(node.getRightChild())) {
				if(isRoot(node)) {
					root = node.getRightChild();
					root.setParent(null);
					node.setParent(node);
					node.setLeftChild(null);
					node.setRightChild(null);
				} else {
					node.getRightChild().setParent(node.getParent());
					if(isLeftChild(node)) {
						node.getParent().setLeftChild(node.getRightChild());
					} else {
						node.getParent().setRightChild(node.getRightChild());
					}
					node.setParent(node);
					node.setLeftChild(null);
					node.setRightChild(null);
				}
			} else if(isInternal(node.getLeftChild()) && isInternal(node.getRightChild())) {
				BinaryTreeNode<Key, Value> repNode = node.getRightChild();
				while(isInternal(repNode.getLeftChild())) {
					repNode = repNode.getLeftChild();
				}
				node.setKey(repNode.getKey());
				node.setValue(repNode.getValue());
				repNode.getRightChild().setParent(repNode.getParent());
				if(isLeftChild(repNode)) {
					repNode.getParent().setLeftChild(repNode.getRightChild());
				} else {
					repNode.getParent().setRightChild(repNode.getRightChild());
				}
				repNode.setParent(repNode);
				repNode.setLeftChild(null);
				repNode.setRightChild(null);
			}
			size--;
			return val2BeRet;
		}
	}

	@Override
	public Iterable<Key> keySet() {
		List<Key> list2BeRet = new ArrayList<Key>();
		for(BinaryTreeNode<Key, Value> node : getNodesInOrder()) {	
			list2BeRet.add(node.getKey());
		}
		return list2BeRet;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(size() == 0) return true;
		return false;
	}

	@Override
	public int height() {
		int hRoot = height(root);
		return hRoot;
	}

	@Override
	public BinaryTreeNode<Key, Value> getRoot() {
		return root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> node2BeRet = validate(node);
		return node2BeRet.getParent();
	}

	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {
		if(node.leftChild != null || node.rightChild != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {
		if(node.leftChild == null && node.rightChild == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {
		if(node == root) return true;
		return false;
	}

	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {
		BinaryTreeNode<Key, Value> buffNode =  treeSearch(root, k);
		if(isExternal(buffNode)) return null;
		return buffNode;
	}

	@Override
	public Value getValue(Key k) {
		BinaryTreeNode<Key, Value> buffNode =  treeSearch(root, k);
		if(isExternal(buffNode)) return null;
		return buffNode.getValue();
	}

	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> node2BeRet = validate(node);
		return node2BeRet.getLeftChild();
	}

	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> node2BeRet = validate(node);
		return node2BeRet.getRightChild();
	}

	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> parent = node.getParent();
		if(parent == null) return null;
		if(parent.getLeftChild() == node) {
			return parent.getRightChild();
		} else {
			return parent.getLeftChild();
		}
	}

	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> p = validate(node);
		BinaryTreeNode<Key, Value> parent = p.getParent();
		if(parent.getLeftChild() == p) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> p = validate(node);
		BinaryTreeNode<Key, Value> parent = p.getParent();
		if(parent.getRightChild() == p) {
			return true;
		} else {
			return false;
		}
	}

	private void subInOrder(BinaryTreeNode<Key, Value> node, List<BinaryTreeNode<Key, Value>> snap) {
		if(!isExternal(node.getLeftChild())) {
			subInOrder(node.getLeftChild(), snap);
		} 
		snap.add(node);
		if(!isExternal(node.getRightChild())) {
			subInOrder(node.getRightChild(), snap);
		}
	}
	
	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
		List<BinaryTreeNode<Key, Value>> snap = new ArrayList<BinaryTreeNode<Key, Value>>();
		if(root != null) {
			subInOrder(root, snap);
		}
		return snap;
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		this.comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {
		BinaryTreeNode<Key, Value> node = treeSearch(root, k);
		if(isInternal(node)) return node;
		while(node != root) {
			if(isLeftChild(node)) {
				return node.getParent();
			} else {
				node = node.getParent();
			}
		}
		return null;
	}

	@Override
	public BinaryTreeNode<Key, Value> floor(Key k) {
		BinaryTreeNode<Key, Value> node = treeSearch(root,k);
		if(isInternal(node)) return node;
		while(!isRoot(node)) {
			if(isRightChild(node)) {
				return node.getParent();
			} else {
				node = node.getParent();
			}
		}
		return null;
	}
 
}
