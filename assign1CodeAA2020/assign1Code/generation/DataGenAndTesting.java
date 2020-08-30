import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import implementation.*;

public class DataGenAndTesting {

    public static void main(String[] args) {

        final int numberOfNodes = 10000; // 2500, 5000, 10000
        List<String> fixedSet = new ArrayList<String>(Arrays.asList(
            "1lyuQ91g8r","E6lRDozHSm","gIUnXzD1GE","BKfz9Xm7EN","A2oEp2SJvP","0kLSnyGrqV","VrX1P3aEog","rPKNY6pYTx","2RH0Y6A5aZ",
            "12hKLfJsTe","G0oSyrWf4h","lUNIXiFTLH","1SYd9CckLJ","uMEpGknRR3","zm4sAnYUml","EPSlc9dgmh","mQjCL7AfIn","iiEkk9AlKw",
            "W536PDPjAZ","061tVgs4xq","7r4EHDHnf0","zhYQ6qezUK","XRXP8UlVo4","QuKaCtTZqq","z6zYotHVqT","6I0MNer1Xt","5BSpkpiraq",
            "8XwFsIA7MO","pbqtllte4M","Mmnuz8oSSJ","9ZFvKRbC3Y","JgbNgHKEqp","JC4NrwaaD8","hQcrigBLXM","SOZfUsJ4f0","0Ow5HmQGeN",
            "1AQ4UFe0Qx","oI6T6HQnND","6qqFlntBcc","cDIWBifQ3l","tNF1mZCtH2","Dic7oVwysH","tLhjmJjJTJ","yQdt6C1IUS","xd1IvdIUPQ",
            "samKaRSe3h","X93byuyCuM","c93lGs6dIo","o1rBR6F6Yo","snI366URp2"
        )); 
        
        RmitMultiset AR = new ArrayMultiset();
        RmitMultiset BST = new BstMultiset();
        RmitMultiset OLL = new OrderedLinkedListMultiset();
        RmitMultiset DLL = new DualLinkedListMultiset();

        // used for scenario 3
        RmitMultiset AROther = new ArrayMultiset();
        RmitMultiset BSTOther = new BstMultiset();
        RmitMultiset OLLOther = new OrderedLinkedListMultiset();
        RmitMultiset DLLOther = new DualLinkedListMultiset();

        double estimatedTimeAR = 0;
        double estimatedTimeBST = 0;
        double estimatedTimeOLL = 0;
        double estimatedTimeDLL = 0;
        long startTime;
        long endTime;


        // For scenario 1: growing
        for(int i = 0; i < numberOfNodes; ++i)
        {
            String randomString = fixedSet.get(new Random().nextInt(fixedSet.size()));
            // for the other multisets used for scenario 3
            String randomStringOther = fixedSet.get(new Random().nextInt(fixedSet.size()));

            AROther.add(randomStringOther);
            BSTOther.add(randomStringOther);
            OLLOther.add(randomStringOther);
            DLLOther.add(randomStringOther);
            
            startTime = System.nanoTime();
            AR.add(randomString);
            endTime = System.nanoTime();
            estimatedTimeAR += ((double) (endTime - startTime)) / Math.pow(10,9);
            
            startTime = System.nanoTime();
            BST.add(randomString);
            endTime = System.nanoTime();
            estimatedTimeBST += ((double) (endTime - startTime)) / Math.pow(10,9);
            
            startTime = System.nanoTime();
            OLL.add(randomString);
            endTime = System.nanoTime();
            estimatedTimeOLL += ((double) (endTime - startTime)) / Math.pow(10,9);

            startTime = System.nanoTime();
            DLL.add(randomString);
            endTime = System.nanoTime();
            estimatedTimeDLL += ((double) (endTime - startTime)) / Math.pow(10,9);
        }

        System.out.println("---SCENARIO 1---");
        printAndReset(estimatedTimeAR, estimatedTimeBST, estimatedTimeOLL, estimatedTimeDLL);


        // For scenario 4: printing     
        startTime = System.nanoTime();
        AR.print();
        endTime = System.nanoTime();
        estimatedTimeAR += ((double) (endTime - startTime)) / Math.pow(10,9);
        
        startTime = System.nanoTime();
        BST.print();
        endTime = System.nanoTime();
        estimatedTimeBST += ((double) (endTime - startTime)) / Math.pow(10,9);
        
        startTime = System.nanoTime();
        OLL.print();
        endTime = System.nanoTime();
        estimatedTimeOLL += ((double) (endTime - startTime)) / Math.pow(10,9);

        startTime = System.nanoTime();
        DLL.print();
        endTime = System.nanoTime();
        estimatedTimeDLL += ((double) (endTime - startTime)) / Math.pow(10,9);
        
        System.out.println("---SCENARIO 4--");
        printAndReset(estimatedTimeAR, estimatedTimeBST, estimatedTimeOLL, estimatedTimeDLL);


        // For scenario 3: intersect
        // the original multisets are still full from scenario 1 at this point
        startTime = System.nanoTime();
        AR.intersect(AROther);
        endTime = System.nanoTime();
        estimatedTimeAR += ((double) (endTime - startTime)) / Math.pow(10,9);
        
        startTime = System.nanoTime();
        BST.intersect(BSTOther);
        endTime = System.nanoTime();
        estimatedTimeBST += ((double) (endTime - startTime)) / Math.pow(10,9);
        
        startTime = System.nanoTime();
        OLL.intersect(OLLOther);
        endTime = System.nanoTime();
        estimatedTimeOLL += ((double) (endTime - startTime)) / Math.pow(10,9);

        startTime = System.nanoTime();
        DLL.intersect(DLL);
        endTime = System.nanoTime();
        estimatedTimeDLL += ((double) (endTime - startTime)) / Math.pow(10,9);
        
        System.out.println("---SCENARIO 3---");
        printAndReset(estimatedTimeAR, estimatedTimeBST, estimatedTimeOLL, estimatedTimeDLL);


        // For scenario 2: shrinking
        for(int i = 0; i < numberOfNodes; ++i)
        {
        	String randomString = fixedSet.get(new Random().nextInt(fixedSet.size()));
            
            startTime = System.nanoTime();
            AR.removeOne(randomString);
            endTime = System.nanoTime();
            estimatedTimeAR += ((double) (endTime - startTime)) / Math.pow(10,9);
            
            startTime = System.nanoTime();
            BST.removeOne(randomString);
            endTime = System.nanoTime();
            estimatedTimeBST += ((double) (endTime - startTime)) / Math.pow(10,9);
            
            startTime = System.nanoTime();
            OLL.removeOne(randomString);
            endTime = System.nanoTime();
            estimatedTimeOLL += ((double) (endTime - startTime)) / Math.pow(10,9);

            startTime = System.nanoTime();
            DLL.removeOne(randomString);
            endTime = System.nanoTime();
            estimatedTimeDLL += ((double) (endTime - startTime)) / Math.pow(10,9);
        }
        
        System.out.println("---SCENARIO 2---");
        printAndReset(estimatedTimeAR, estimatedTimeBST, estimatedTimeOLL, estimatedTimeDLL);
    }

    // Prints estimated times to the screen and resets the timers
    public static void printAndReset(double estimatedTimeAR, double estimatedTimeBST, double estimatedTimeOLL, double estimatedTimeDLL)
    {
        // System.out.println("time taken Array MS =               " + estimatedTimeAR + " sec");
        // System.out.println("time taken Binary Search Tree MS =  " + estimatedTimeBST + " sec");
        // System.out.println("time taken Ordered Linked List MS = " + estimatedTimeOLL + " sec");
        // System.out.println("time taken Dual Linked List MS=     " + estimatedTimeDLL + " sec");
        System.out.println(estimatedTimeAR);
        System.out.println(estimatedTimeBST);
        System.out.println(estimatedTimeOLL);
        System.out.println(estimatedTimeDLL);
        System.out.println("----------------\n");

        estimatedTimeAR = 0;
        estimatedTimeBST = 0;
        estimatedTimeOLL = 0;
        estimatedTimeDLL = 0;
    }
}