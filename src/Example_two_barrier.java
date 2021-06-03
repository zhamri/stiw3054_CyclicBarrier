import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class MyCyclicBarrier implements Runnable {

    CyclicBarrier barrier1, barrier2;

    public MyCyclicBarrier(CyclicBarrier barrier1, CyclicBarrier barrier2) {
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " waiting at barrier 1");
            this.barrier1.await();

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " waiting at barrier 2");
            this.barrier2.await();

            System.out.println(Thread.currentThread().getName() + " done!");

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class Main {

    public static void main(String[] args) {

        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println("r1 is executed ");
            }
        };
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println("r2 is executed ");
            }
        };

        CyclicBarrier barrier1 = new CyclicBarrier(2, r1);
        CyclicBarrier barrier2 = new CyclicBarrier(2, r2);

        MyCyclicBarrier myCyclicBarrier1 = new MyCyclicBarrier(barrier1, barrier2);
        MyCyclicBarrier myCyclicBarrier2 = new MyCyclicBarrier(barrier1, barrier2);

        new Thread(myCyclicBarrier1).start();
        new Thread(myCyclicBarrier2).start();
    }
}


/*
Thread-0 waiting at barrier 1
Thread-1 waiting at barrier 1
r1 is executed
Thread-1 waiting at barrier 2
Thread-0 waiting at barrier 2
r2 is executed
Thread-0 done!
Thread-1 done!
 */