package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> myCompare;
    public MaxArrayDeque(){
        super();
    }
    public MaxArrayDeque(Comparator<T> c){
        myCompare = c;
    }
    public T max(){
        return max(myCompare);
    }
    public T max(Comparator<T> c){
        T maxElement = null;
        for (int i = 0;i < size() - 1;i++) {
            int comparedValue = c.compare(super.get(i),super.get(i + 1));
            if(comparedValue < 0){
                maxElement = super.get(i + 1);
            }else{
                maxElement = super.get(i);
            }
        }
        return maxElement;
    }
}
