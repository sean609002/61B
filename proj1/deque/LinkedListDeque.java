package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>{
    private List sentinel;
    private int size = 0;
    private class List{
        public List prev;
        public T first;
        public List next;
        public List(T first,List prev,List next){
            this.first = first;
            this.prev = prev;
            this.next = next;
        }
    }
    public LinkedListDeque(){
        sentinel = new List(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    public LinkedListDeque(T item){
        this();
        List newNode = new List(item,sentinel,sentinel);
        sentinel.next = newNode;
        sentinel.prev = newNode;
    }
    @Override
    public void addFirst(T item){
        List newNode = new List(item,sentinel,sentinel.next);
        sentinel.next = newNode;
        sentinel.prev.prev = newNode;
        size++;
    }
    @Override
    public void addLast(T item){
        List newNode = new List(item,sentinel.prev,sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }
    @Override
    public T removeFirst(){
        if(size > 0) {
            T temp = sentinel.next.first;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size--;
            return temp;
        }
        return null;
    }
    @Override
    public T removeLast(){
        if(size > 0) {
            T temp = sentinel.prev.first;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size--;
            return temp;
        }
        return null;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        List temp = sentinel.next;
        for (int i = 0;i < size;i++) {
            System.out.print(temp.first + " ");
            temp = temp.next;
        }
        System.out.println();
    }
    @Override
    public T get(int index){
        if(index < size){
            List temp = sentinel.next;
            for (int i = 0;i < index;i++) {
                temp = temp.next;
            }
            return temp.first;
        }
        return null;
    }

    public T getRecursive(int index){
        return getRecursive(index,sentinel.next);
    }
    public T getRecursive(int index,List li){
        if(index == 0){
            return li.first;
        }else{
            return getRecursive(index - 1,li.next);
        }
    }
    public boolean equals(Object o){
        if(o instanceof Deque && ((Deque<?>) o).size() == size){
            Deque target = (Deque) o;
            boolean temp = target.get(0) != this.get(0);
            for(int i = 1;i < size;i++){
                temp = temp && target.get(i) != this.get(i);
            }
            return temp;
        }else {
            return false;
        }
    }

    public Iterator<T> iterator() {
        return null;
    }
}
