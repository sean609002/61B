package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }
    public static void timeGetLast(){
        timeGetLast(8,10000);
    }
    public static void timeGetLast(int testCount,int ops) {
        // TODO: YOUR CODE HERE
        double timeInSeconds;
        SLList<Integer> test = new SLList<Integer>();
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        initList(test,1000);
        for(int i = 0;i < testCount;i++){
            timeInSeconds = getLastTime(test,ops);
            Ns.addLast(test.size());
            times.addLast(timeInSeconds);
            opCounts.addLast(ops);
            enlargeList(test,2);
        }
        printTimingTable(Ns,times,opCounts);
    }

    public static void initList(SLList<Integer> list,int size){
        for(int j = 0;j < size;j++){
            list.addFirst(1);
        }
    }
    //addFirst(1) until the size = original size * factor
    public static void enlargeList(SLList<Integer> list,int factor){
        int times = list.size() * (factor - 1);
        for(int j = 0;j < times;j++){
            list.addFirst(1);
        }
    }
    public static double getLastTime(SLList<Integer> list,int ops){
        Stopwatch sw = new Stopwatch();
        for(int j = 0;j < ops;j++){
            list.getLast();
        }
        return sw.elapsedTime();
    }
}
