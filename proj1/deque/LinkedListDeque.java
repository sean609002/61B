package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>,Iterable<T> {
    private List sentinel;
    private int size = 0;
    private class List {
        public List prev;
        public T first;
        public List next;
        public List(T first, List prev, List next) {
            this.first = first;
            this.prev = prev;
            this.next = next;
        }
    }
    public LinkedListDeque() {
        sentinel = new List(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    public LinkedListDeque(T item) {
        this();
        List newNode = new List(item, sentinel, sentinel);
        sentinel.next = newNode;
        sentinel.prev = newNode;
    }
    @Override
    public void addFirst(T item) {
        List newNode = new List(item, sentinel, sentinel.next);
        sentinel.next = newNode;
        sentinel.prev.prev = newNode;
        size++;
    }
    @Override
    public void addLast(T item) {
        List newNode = new List(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }
    @Override
    public T removeFirst() {
        if (size > 0) {
            T temp = sentinel.next.first;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size--;
            return temp;
        }
        return null;
    }
    @Override
    public T removeLast() {
        if (size > 0) {
            T temp = sentinel.prev.first;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size--;
            return temp;
        }
        return null;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        List temp = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.first + " ");
            temp = temp.next;
        }
        System.out.println();
    }
    @Override
    public T get(int index) {
        if (index < size) {
            List temp = sentinel.next;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp.first;
        }
        return null;
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }
    public T getRecursive(int index, List li) {
        if (index == 0) {
            return li.first;
        } else {
            return getRecursive(index - 1, li.next);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (o instanceof LinkedListDeque target) {
            if (target.size() != this.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (target.get(i) != this.get(i)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    public class LinkedListDequeIterator implements Iterator<T> {
        private int index = 0;
        public boolean hasNext() {
            return index < size();
        }

        public T next() {
            T nextElement = get(index);
            index++;
            return nextElement;
        }
    }
}
