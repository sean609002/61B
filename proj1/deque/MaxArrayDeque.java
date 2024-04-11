package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> myCompare;
    public MaxArrayDeque(Comparator<T> c) {
        myCompare = c;
    }
    public T max() {
        return max(myCompare);
    }
    public T max(Comparator<T> c) {
        T maxElement = null;
        if (size() == 0) {
            return maxElement;
        }
        maxElement = super.get(0);
        for (int i = 1; i < size() - 1; i++) {
            int comparedValue = c.compare(maxElement, super.get(i));
            if (comparedValue < 0) {
                maxElement = super.get(i);
            }
        }
        return maxElement;
    }
}
