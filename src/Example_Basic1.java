import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CB_Demo {

    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3);
        Thread t1 = new Thread(new Task1(cb));
        Thread t2 = new Thread(new Task2(cb));
        System.out.println("Starting threads ... ");
        t1.start();
        t2.start();

        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": Continue the process again ... ");
    }
}

class Task1 implements Runnable {
    CyclicBarrier cb;

    Task1(CyclicBarrier cb) {
        this.cb = cb;
    }

    @Override
    public void run() {
        System.out.println("Task 1 by " + Thread.currentThread().getName());
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class Task2 implements Runnable {
    private CyclicBarrier cb;

    Task2(CyclicBarrier cb) {
        this.cb = cb;
    }

    @Override
    public void run() {
        System.out.println("Task 2 by " + Thread.currentThread().getName());
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}


/*
Starting threads ...
Task 2 by Thread-1
Task 1 by Thread-0
main: Continue the process again ...
 */