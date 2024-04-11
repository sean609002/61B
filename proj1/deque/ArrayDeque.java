package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size = 0;
    private double usage;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }
    @Override
    public void addFirst(T item) {
        if (size() == items.length) {
            reSize(items.length, usage);
        }
        items[nextFirst] = item;
        nextFirst = updateNextFirstReverseNextLast(nextFirst);
        size++;
        updateUsage();
    }
    @Override
    public void addLast(T item) {
        if (size() == items.length) {
            reSize(items.length, usage);
        }
        items[nextLast] = item;
        nextLast = updateNextLastReverseNextFirst(nextLast);
        size++;
        updateUsage();
    }
    public void updateUsage() {
        usage = size / (double) items.length;
    }
    public void reSize(int length, double usage) {
        T[] newItemArr;
        if (usage > 0.9) {
            newItemArr = (T[]) new Object[length * 2];
            reSizeHelper(newItemArr);
        } else if (usage < 0.25 && size() > 8) {
            newItemArr = (T[]) new Object[length / 4];
            reSizeHelper(newItemArr);
        }
    }
    public void reSizeHelper(T[] newItemArr) {
        for (int i = 1; i < size() + 1; i++) {
            newItemArr[i] = get(i - 1);
        }
        nextFirst = 0;
        nextLast = size() + 1;
        items = newItemArr;
    }
    @Override
    public T removeFirst() {
        if (size() > 0) {
            nextFirst = updateNextLastReverseNextFirst(nextFirst);
            T temp = items[nextFirst];
            items[nextFirst] = null;
            size--;
            updateUsage();
            reSize(items.length, usage);
            return temp;
        }
        return null;
    }
    @Override
    public T removeLast() {
        if (size() > 0) {
            nextLast = updateNextFirstReverseNextLast(nextLast);
            T temp = items[nextLast];
            items[nextLast] = null;
            size--;
            updateUsage();
            reSize(items.length, usage);
            return temp;
        }
        return null;
    }
    @Override
    public T get(int index) {
        int first = updateNextLastReverseNextFirst(nextFirst);
        int targetIndex = first + index;
        if (targetIndex >= items.length) {
            targetIndex = targetIndex - items.length;
        }
        return items[targetIndex];
    }

    public Iterator<T> iterator() {
        return null;
    }


    @Override
    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public void printDeque() {
        int first = updateNextLastReverseNextFirst(nextFirst);
        for (int i = 0; i < size(); i++) {
            System.out.print(items[first] + " ");
            first = updateNextLastReverseNextFirst(first);
        }
        System.out.println();
    }
    public int updateNextFirstReverseNextLast(int next) {
        next--;
        if (next < 0) {
            next = items.length - 1;
        }
        return next;
    }
    public int updateNextLastReverseNextFirst(int next) {
        next++;
        if (next == items.length) {
            next = 0;
        }
        return next;
    }
}
