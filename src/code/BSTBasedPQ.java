package code;

import java.util.Comparator;
import java.util.*;

import given.DefaultComparator;
import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {
	
	private Comparator<Key> comparator;
	
	public BSTBasedPQ() {
		super();
	}

	@Override
	public void insert(Key k, Value v) {
		this.put(k, v);
		
	}

	@Override
	public Entry<Key, Value> pop() {
		if(isEmpty()) return null;
		BinaryTreeNode<Key, Value> node = this.root;
		while(isInternal(node.getLeftChild())) {
			node = node.getLeftChild();
		}
		Entry<Key, Value> ent2BeRet = new Entry<Key, Value>(node.getKey(), node.getValue());
		this.remove(node.getKey());
		return ent2BeRet;
	}

	@Override
	public Entry<Key, Value> top() {
		if(isEmpty()) return null;
		BinaryTreeNode<Key, Value> node = this.getRoot();
		while(isInternal(node.getLeftChild())) {
			node = node.getLeftChild();
		}
		return node;
		
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		Key key2BeRet = entry.getKey();
		Value val2BeAdded = entry.getValue();
		if(isExternal(this.getNode(key2BeRet))) return null;
		if(this.getNode(key2BeRet).getKey().equals(entry.getKey()) && this.getNode(key2BeRet).getValue().equals(entry.getValue())) {
			this.remove(key2BeRet);
			this.put(k, val2BeAdded);
			return key2BeRet;	
		}
		return null;
		
		
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		List<BinaryTreeNode<Key, Value>> nodes = this.getNodesInOrder();
		Key key2BeRet = null;
		for(int i=0; i<nodes.size(); i++) {
			if(nodes.get(i).getValue().equals(v)) {
				key2BeRet = nodes.get(i).getKey();
				this.remove(key2BeRet);
				this.put(k, v);
			}
		}
		return key2BeRet;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		Value val2BeRet = null;
		if(isExternal(this.getNode(entry.getKey()))) return null;
		if(this.getNode(entry.getKey()).getKey().equals(entry.getKey()) && this.getNode(entry.getKey()).getValue().equals(entry.getValue())) {
			val2BeRet = this.getNode(entry.getKey()).getValue();
			this.getNode(entry.getKey()).setValue(v);
		}
		return val2BeRet;
	}
	
}
