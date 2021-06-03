import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Hea {

    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3, new YourResult());
        Thread t1 = new Thread(new YourExam("Exam-1", 80, cb));
        Thread t2 = new Thread(new YourExam("Exam-2", 85, cb));
        Thread t3 = new Thread(new YourExam("Exam-3", 90, cb));
        t1.start();
        t2.start();
        t3.start();

        System.out.println("Done ");
    }
}

class YourExam implements Runnable {
    private String examCode;
    private int mark;
    private CyclicBarrier cb;

    YourExam(String examCode, int mark, CyclicBarrier cb){
        this.examCode = examCode;
        this.mark = mark;
        this.cb = cb;
    }
    @Override
    public void run() {
        System.out.println(examCode + ": " + mark );
        try{
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
Done
Exam-3: 90
Exam-1: 80
Exam-2: 85
You get the results
 */