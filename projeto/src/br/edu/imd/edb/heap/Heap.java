package br.edu.imd.edb.heap;


import br.edu.imd.edb.tree.Node;
import br.edu.imd.edb.tree.Tree;

import java.util.Arrays;

public class Heap {
	private Tree[] node;
	private int size;//quantos elementos tem
	private int capacity;//quantos elementos cabem

	public Heap() {
		this(10);
	}

	public Heap(int capacity) {
		node = new Tree[capacity];
		this.size = 0;
		this.capacity = capacity;
	}

	public void insert(int x) {
		insert(new Tree(), x);
	}

	public void insert(Tree pessoa, int x) {
		ensureCapacity();
		pessoa.insert(x);
		node[getSize()] = pessoa;
		heapifyUp(getSize());
		size++;
	}
	public void insert(Tree pessoa) {
		ensureCapacity();
		node[getSize()] = pessoa;
		heapifyUp(getSize());
		size++;
	}

	private void heapifyUp(int index) {
		int parentIndex = getParentIndex(index);

		if (parentIndex < 0) {
			return;
		}

		Tree pai    = node[parentIndex];
		Tree pessoa = node[index];

		if (pessoa.getRoot().getValue().getValue() > pai.getRoot().getValue().getValue()) {
			node[index]   = pai;
			node[parentIndex] = pessoa;
			heapifyUp(parentIndex);
		}
	}

	public int getParentIndex(int index) {
		return (int) Math.floor((index - 1) / 2);
	}

	private void ensureCapacity() {
		if (size == capacity) {
			node = Arrays.copyOf(node, capacity * 2);
			capacity = capacity * 2;
		}
	}

	public int getSize() {
		return size;
	}

	public Tree peek() {
		return peek(0);
	}

	public Tree peek( int index) {
		if (getSize() == 0) {
			return null;
		}
		return node[index];
	}

	public void remove() {
		node[0] = node[getSize() - 1];
		node[getSize() - 1] = null;
		size--;
		heapifyDown(0);
	}

	private void heapifyDown(int index) {
		int leftChild = index * 2 + 1;
		int rightChild = index * 2 + 2;

		int childIndex = -1;
		if (leftChild < getSize()) {
			childIndex = leftChild;
		}

		if (childIndex == -1) {
			return;
		}

		if (rightChild < getSize()) {
			if (node[rightChild].getRoot().getValue().getValue() > node[leftChild].getRoot().getValue().getValue()) {
				childIndex = rightChild;
			}
		}

		if (node[index].getRoot().getValue().getValue() < node[childIndex].getRoot().getValue().getValue()) {
			Tree tmp          = node[index];
			node[index]      = node[childIndex];
			node[childIndex] = tmp;
			heapifyDown(childIndex);
		}
	}







}


