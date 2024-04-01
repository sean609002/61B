package tester;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

import static org.junit.Assert.*;

public class TestArrayDequeEC {
    @Test
    public void addRemoverandomTest(){
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        String errorMessage = "";
        for (int i = 0; i < 1000; i += 1) {
            Integer rand = StdRandom.uniform(0,5);
            if(rand == 0){
                errorMessage += "addFirst(" + i +")\n";
                test.addFirst(i);
                solution.addFirst(i);
            }else if(rand == 1){
                errorMessage += "addLast(" + i +")\n";
                test.addLast(i);
                solution.addLast(i);
            }else if(rand == 2 && solution.size() > 0){
                errorMessage += "removeFirst()\n";
                assertEquals(errorMessage,test.removeFirst(),solution.removeFirst());
            }else if(rand == 3 && solution.size() > 0){
                errorMessage += "removeLast()\n";
                assertEquals(errorMessage,test.removeLast(),solution.removeLast());
            }

        }
    }
}
