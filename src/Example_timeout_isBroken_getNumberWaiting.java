import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CarryMark implements Runnable {
    public static int cm = 0;

    public void run() {
        cm = 15 + 15 + 30;
        System.out.println("Calculate carry marks ...");
        try {
            TotalMarks.barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class FinalExam implements Runnable {
    public static double fe = 0;

    public void run() {
        System.out.println("Is the barrier broken? - " + TotalMarks.barrier.isBroken());
        fe = 100 * 0.4;
        System.out.println("Calculate final exam ...");
        try {
            TotalMarks.barrier.await(2300, TimeUnit.MILLISECONDS);
            System.out.println("Number of threads waiting at the barrier = " + TotalMarks.barrier.getNumberWaiting());
        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}


class TotalMarks implements Runnable {
    public static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) {

        TotalMarks totalMarks = new TotalMarks();
        Thread t1 = new Thread(totalMarks);
        t1.start();
    }

    public void run() {
        System.out.println("Number of threads = " + barrier.getParties());
        System.out.println("Mark = " + (CarryMark.cm + FinalExam.fe));

        CarryMark carryMark = new CarryMark();
        FinalExam finalExam = new FinalExam();
        Thread t1 = new Thread(carryMark);
        Thread t2 = new Thread(finalExam);
        t1.start();
        t2.start();

        try {
            TotalMarks.barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("Total Marks = " + (CarryMark.cm + FinalExam.fe));
    }
}


/*
Number of threads = 3
Mark = 0.0
Calculate carry marks ...
Is the barrier broken? - false
Calculate final exam ...
Number of threads waiting at the barrier = 0
Total Marks = 100.0
 */
