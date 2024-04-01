package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
      BuggyAList<Integer> test = new BuggyAList<>();
      AListNoResizing<Integer> truth = new AListNoResizing<>();
      for (int i = 4;i < 7 ; i++) {
        test.addLast(i);
        truth.addLast(i);
      }
      for (int i = 0;i < 3 ;i++ ) {
        assertEquals(test.removeLast(),truth.removeLast());
      }
    }
    @Test
    public void randomizedTest(){
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> buggy = new BuggyAList<>();
      int N = 50000;
      for (int i = 0; i < N; i += 1) {
        int operationNumber = StdRandom.uniform(0, 4);
        if (operationNumber == 0) {
          // addLast
          int randVal = StdRandom.uniform(0, 100);
          L.addLast(randVal);
          buggy.addLast(randVal);
          //System.out.println("addLast(" + randVal + ")");
        } else if (operationNumber == 1) {
          // size
          int LSize = L.size();
          int buggySize = buggy.size();
          //System.out.println("LSize: " + LSize + " buggySize: " + buggySize);
          assertEquals(LSize,buggySize);
        } else if(operationNumber == 2 && L.size() != 0 && buggy.size() != 0){
          //getLast
          int LLast = L.getLast();
          int buggyLast = buggy.getLast();
          //System.out.println("getLast(): LLast = " + LLast + "getLast(): buggyLast = " + buggyLast);
          assertEquals(LLast,buggyLast);

        } else if(operationNumber == 3 && L.size() != 0 && buggy.size() != 0){
          //removeLast
          int LLast = L.removeLast();
          int buggyLast = buggy.removeLast();
          //System.out.println("removeLast(): LLast = " + LLast + "removeLast(): buggyLast = " + buggyLast);
          assertEquals(LLast,buggyLast);
        }
      }
    }
}
