package Example_Reset;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Hea {

    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(4, new YourResult());
        System.out.println("Start Exam ");
        Thread t1 = new Thread(new YourExam("Exam-1", 80, cb));
        Thread t2 = new Thread(new YourExam("Exam-2", 85, cb));
        Thread t3 = new Thread(new YourExam("Exam-3", 90, cb));
        t1.start();
        t2.start();
        t3.start();

        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("Replacement Exam ");
        cb.reset();

        Thread t4 = new Thread(new YourExam("Replacement-Exam-1", 50, cb));
        Thread t5 = new Thread(new YourExam("Replacement-Exam-2", 51, cb));
        Thread t6 = new Thread(new YourExam("Replacement-Exam-3", 52, cb));
        t4.start();
        t5.start();
        t6.start();

        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("Done !!!");
    }
}

class YourExam implements Runnable {
    private String examCode;
    private int mark;
    private CyclicBarrier cb;

    YourExam(String examCode, int mark, CyclicBarrier cb) {
        this.examCode = examCode;
        this.mark = mark;
        this.cb = cb;
    }

    @Override
    public void run() {
        System.out.println(examCode + ": " + mark);
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(e);
        }
    }
}

class YourResult implements Runnable {
    @Override
    public void run() {
        System.out.println("You get the results");
    }
}


/*
Start Exam
Exam-3: 90
Exam-1: 80
Exam-2: 85
You get the results
Replacement Exam
Replacement-Exam-1: 50
Replacement-Exam-2: 51
Replacement-Exam-3: 52
You get the results
Done !!!
 */