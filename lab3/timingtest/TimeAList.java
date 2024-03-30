package timingtest;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    //ops:number of operations made to addLast()
    //N:size of data structure
    //times:total amount of time for all operations
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
        timeAListConstruction();
    }
    public static void timeAListConstruction(){
        timeAListConstruction(8,1000);
    }
    public static void timeAListConstruction(int testCount,int initOps) {
        // TODO: YOUR CODE HERE
        double timeInSeconds;
        AList<Integer> test;
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        for(int i = 0;i < testCount;i++){
            test = new AList<Integer>();
            timeInSeconds = addLastTime(test,initOps);
            Ns.addLast(test.size());
            times.addLast(timeInSeconds);
            opCounts.addLast(initOps);
            initOps *= 2;
        }
        printTimingTable(Ns,times,opCounts);
    }

    //return how much time it takes to finish addLast() for "count" times
    public static double addLastTime(AList<Integer> list,int count){
        Stopwatch sw = new Stopwatch();
        for(int j = 0;j < count;j++){
            list.addLast(1);
        }
        return sw.elapsedTime();
    }
}
