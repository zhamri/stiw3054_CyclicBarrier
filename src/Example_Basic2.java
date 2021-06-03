import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Example_Basic2 {

    private static class Task implements Runnable {

        private final CyclicBarrier barrier;

        public Task(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {

        final CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                //This task will be executed once all threads reach the barrier
                System.out.println("All threads are arrived at barrier, lets continue ...");
            }
        });

        Thread t1 = new Thread(new Task(cb));
        Thread t2 = new Thread(new Task(cb));
        Thread t3 = new Thread(new Task(cb));
        t1.start();
        t2.start();
        t3.start();
    }
}


/*
Thread-2 is waiting on barrier
Thread-0 is waiting on barrier
Thread-1 is waiting on barrier
All threads are arrived at barrier, lets continue ...
Thread-1 has crossed the barrier
Thread-2 has crossed the barrier
Thread-0 has crossed the barrier
 */
