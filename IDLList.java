package hw3;

import java.util.ArrayList;

public class IDLList<E> {
	private static class Node<E> {
		// data fields
		private E data;
		private Node<E> next;
		private Node<E> prev;
		// Constructor
		
		public Node(E elem, Node<E> prev, Node<E> next) {
			super();
			this.data = elem;
			this.next = next;
			this.prev = prev;
		}
		public Node(E elem) {
			super();
			this.data = elem;
		}
	}
	
	//data fields
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;
	
	//constructor
	public IDLList(){
		head = null;
		tail = null;
		size = 0;
		this.indices = new ArrayList<Node<E>>();
		//thing
	}
	
	//methods
	public boolean add(E elem) {
		//adds element to head
		if(size == 0) { //if list is empty
			head = new Node<E>(elem);
			tail = head;
			size++;
			indices.add(0,head);
		}
		else { //Non-empty list
			Node<E> newHead = new Node<E>(elem,null,head);
			head.prev = newHead;
			head = newHead;
			size++;
			indices.add(0, newHead);
		}
		return true;
	}
	
	public boolean add(int index,E elem) {
		//adds elem at index
		//uses indices array
		if(index < 0 || index > size ) {
			throw new IllegalStateException("index out of bounds");
		}
		else if(index == 0) { //adding to head
			add(elem);
		}
		else if(index == size) { //adding to tail
			append(elem);
		}
		else { //other indices
			Node<E> current = indices.get(index-1);
			Node<E> temp = new Node<E>(elem,current,current.next);
			current.next.prev = temp;
			current.next = temp;
			indices.add(index, temp);
			size++;
		}
		return true;
		
	}
	
	public boolean append(E elem) {
		//adds element to the end of list and makes it the tail, increments size
		
		if(head == null) { //empty list case
			head = new Node<E>(elem,null,null);
			tail = head;
			size++;
			indices.add(tail);
		}
		else { //non empty list
			tail.next = new Node<E>(elem,tail,null);
			tail = tail.next;
			size++;
			indices.add(tail);
		}
		return true;
	}
	
	public E get(int index) {
		//returns object from indices without removing
		if(index < 0 || index > size - 1) {
			throw new IllegalArgumentException("Index out of bounds");
		}
		else {
			return indices.get(index).data;
		}
		
	}
	
	public E getHead() {
		//returns object at head without removing
		if(head == null) {//empty list
			throw new IllegalArgumentException("Head is empty");
		}
		else {//non-empty list
			return head.data;
		}
	}
	
	public E getLast() {
		//returns tail without removing
		if(tail == null) {//empty list
			throw new IllegalArgumentException("Tail is empty");
		}
		else {//non-empty list
			return tail.data;
		}
	}
	
	public int size() {
		return size;
	}
	
	public E remove() {
		//removes object at head decrements size
		if(head==null) {//empty list case
			throw new IllegalStateException("No object at head");
		}
		else {//non-empty list case
			E temp = head.data;
			if(head.next != null) {//more than 1 item in the list
				head.next.prev = null;
				head = head.next;
				size--;
				indices.remove(0);
			}
			else {//only 1 item in list
				head = null;
				tail = null;
				size--;
				indices.remove(0);
			}
			return temp;
		}
	}
	
	public E removeLast() {
		//removes object at tail
		if(tail == null) {
			throw new IllegalStateException("No object at tail");
		}
		else {//non-empty list case
			E temp = tail.data;
			if(size == 1) {//only 1 item in list
				return remove();
			}
			else if(tail.prev != null) {//more than 1 item in the list
				tail.prev.next = null;
				tail = tail.prev;
				indices.remove(size-1);
				size--;
			}
			return temp;
		}
	}
	
	public E removeAt(int index) {
		//removes object at index and decrements size
		if(index < 0 || index > size - 1) {
			throw new IllegalStateException("Index out of bounds");
		}
		else if(index == 0) { //removing first item
			return remove();
		}
		else if(index == size-1) { //removing last item
			return removeLast();
		}
		else { //removing any other item
			Node<E> current = indices.get(index);
			current.next.prev = current.prev;
			current.prev.next = current.next;
			indices.remove(index);
			size--;
			return current.data;
		}
	}
	
	public boolean remove(E elem) {
		//removes first occurance of elem
		if(size == 0) {
			throw new IllegalStateException("List is empty");
		}
		Node<E> current = head;
		int i = 0;
		while(current.data != elem && i<size-1) { //exits when current.data = elem or elem is not in elem
			current = current.next;
			i++;
		}
		if(i == size) { //no elem in list
			return false;
		}
		else {
			removeAt(i);
			return true;
		}
		
		//returns true if object is removed
		//returns false if there was no elem
		//thing
	}
	
	public String toString() {
		
		if(size == 0) {
			return "[]";
		}
		StringBuilder s = new StringBuilder();
		Node<E> current = head;
		s.append("[");
		while (current!=null) {
			s.append(current.data.toString()+",");
			current = current.next;
		}
		s.deleteCharAt(s.length()-1);
		s.append("]");
		return s.toString();
	}
	
	public String printReverse() {
		
		StringBuilder s = new StringBuilder();
		Node<E> current = tail;
		s.append("[");
		while (current!=null) {
			s.append(current.data.toString()+",");
			current = current.prev;
		}
		s.deleteCharAt(s.length()-1);
		s.append("]");
		return s.toString();
	}
	
	public String indicesToString() {
		StringBuilder s = new StringBuilder();
		s.append("[");
		for(Node<E> node : indices) {
			s.append(node.data + ",");
		}
		s.deleteCharAt(s.length()-1);
		s.append("]");
		return s.toString();
	}
	
	public void summary() {
		System.out.println("toString     "+this.toString());
		System.out.println("indices      " +this.indicesToString());
		System.out.println("Reverse      "+this.printReverse());
		System.out.println("size          "+this.size());
		System.out.println("indices size  "+this.size());
		System.out.println("head          "+this.getHead());
		System.out.println("tail          "+this.getLast());
	}
	
	public static void main(String[] args) {
		IDLList<Integer> l = new IDLList<Integer>();
		
		//one element
		//for(int i = 0; i<1;i++) {l.add(i);}
		
		//two elements
		//for(int i = 0; i<2;i++) {l.add(i);}
		
		//long
		//for(int i = 0; i<10;i++) {l.add(i);}
		
		//repeat 0
		//for(int i = 0; i<10;i++) {l.add(0);}
		
		
		
		l.summary();
		
		
	}
}