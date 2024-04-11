package test;

import deque.MaxArrayDeque;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxArrayTest {
    @Test
    public void maxTest() {
        MaxArrayDeque<Integer> test = new MaxArrayDeque<>(new MyCompare());
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                test.addFirst(i);
            } else {
                test.addLast(i);
            }
        }
        assertEquals(test.max(), (Integer) 99);
    }
}

