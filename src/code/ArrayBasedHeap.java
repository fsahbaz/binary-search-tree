package code;

import java.util.*;

import given.DefaultComparator;
import given.Entry;
import given.iAdaptablePriorityQueue;
import given.iBinarySearchTree;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	private ArrayList<Entry<Key, Value>> myHeap = new ArrayList<Entry<Key, Value>>();;
	private Comparator<Key> comparator; 
	
	public ArrayBasedHeap() { }
	
	protected int parent(int i) {
		return (i-1)/2;
	}
	
	protected int leftChild(int i) {
		return (2*i + 1);
	}
	
	protected int rightChild(int i) {
		return (2*i + 2);
	}
	
	protected boolean hasLeftChild(int i) {
		if(leftChild(i) < myHeap.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean hasRightChild(int i) {
		if(rightChild(i) < myHeap.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void swap(int i, int j) {
		Entry<Key, Value> tempEnt = myHeap.get(i);
		myHeap.set(i, myHeap.get(j));
		myHeap.set(j, tempEnt);
	}
	
	protected void upheap(int i) {
		while(i > 0) { 
			int p = parent(i);
			if(this.comparator.compare(myHeap.get(i).getKey(), myHeap.get(p).getKey()) >= 0) break;
			swap(i,p);
			i = p;
		}
	}
	
	protected void downheap(int i) {
		while(hasLeftChild(i)) {
			
			int leftInd = leftChild(i);
			int smallChildInd = leftInd;
			if(hasRightChild(i)) {
				int rightInd = rightChild(i);
				if(this.comparator.compare(myHeap.get(leftInd).getKey(), myHeap.get(rightInd).getKey()) > 0) {
					smallChildInd = rightInd;
				} 
			}
			if(this.comparator.compare(myHeap.get(smallChildInd).getKey(), myHeap.get(i).getKey()) >= 0) {
				break;
			}
			swap(i, smallChildInd);
			i = smallChildInd;
			
		}
	}

	protected void bubble(int i) {
		if(i > 0 && this.comparator.compare(myHeap.get(i).getKey(), myHeap.get(parent(i)).getKey()) < 0) {
			upheap(i);
		} else {
			downheap(i);
		}
	}
	
	@Override
	public int size() {
		return myHeap.size();
	}

	@Override
	public boolean isEmpty() {
		if(size() == 0) return true;
		return false;
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		this.comparator = C;
		
	}

	@Override
	public void insert(Key k, Value v) {
		
		Entry<Key, Value> newest = new Entry<Key, Value>(k, v);
		myHeap.add(newest);
		upheap(size() - 1);
		
	}

	@Override
	public Entry<Key, Value> pop() {
		if(myHeap.isEmpty()) return null;
		Entry<Key, Value> toBeRet = myHeap.get(0);
		swap(0, size()-1);
		myHeap.remove(myHeap.size()-1);
		downheap(0);
		return toBeRet;
	}

	@Override
	public Entry<Key, Value> top() {
		if(myHeap.isEmpty()) return null;
		return myHeap.get(0);
	}

	@Override
	public Value remove(Key k) {
		int loc = 0;
		Value val2BeRet = null;
		for(Entry<Key, Value> ent : myHeap) {
			if(ent.getKey() == k) {
				loc = myHeap.indexOf(ent);
				val2BeRet = ent.getValue();
			} 
		}
		if(loc == size() - 1) {
			myHeap.remove(size() - 1);
		} else {
			swap(loc, size()-1);
			myHeap.remove(size() - 1);
			bubble(loc);
		}
		return val2BeRet;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		Key key2BeRet = null;
		if(myHeap.get(myHeap.indexOf(entry)).getKey() == entry.getKey() && myHeap.get(myHeap.indexOf(entry)).getValue() == entry.getValue()) {
			key2BeRet = entry.getKey();
			entry.setKey(k);
			bubble(myHeap.indexOf(entry));
		} 
		return key2BeRet;
		
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		Key key2BeRet = null;
		for(int i=0; i<myHeap.size(); i++) {
			if(myHeap.get(i).getValue().equals(v)) {
				key2BeRet = myHeap.get(i).getKey();
				replaceKey(myHeap.get(i), k);
			}
		}
		return key2BeRet;
		
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		Value val2BeRet = null;
		if(!myHeap.contains(entry)) return null;
		if(myHeap.get(myHeap.indexOf(entry)).getKey() == entry.getKey() && myHeap.get(myHeap.indexOf(entry)).getValue() == entry.getValue()) {
			val2BeRet = entry.getValue();
			entry.setValue(v);
		} 
		return val2BeRet;
	}
  
}
