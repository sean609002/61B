package deque;

import java.util.Iterator;

public interface Deque<T> {
    public void addFirst(T item);
    public void addLast(T item);
    public default boolean isEmpty() {
        return size() == 0;
    }
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);
    public Iterator<T> iterator();
    public default boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Deque target) {
            if (target.size() != this.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                if (target.get(i) != this.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
