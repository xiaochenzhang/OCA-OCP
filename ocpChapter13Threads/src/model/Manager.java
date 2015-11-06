package model;

public class Manager extends Thread {

    @Override
    public void run() {
        while (true) {
            // sync myself with chiefs
            synchronized (this) {
                // emergency meeting for few seconds
                System.out.println("---------------------------" + Thread.currentThread().getName()
                        + " : emergency meeting guys ---------------------------");
                try {
                    Thread.sleep(5000);
                } catch (final InterruptedException ex) {
                }
                System.out.println("---------------------------" + Thread.currentThread().getName()
                    + " : meeting finished ------------------------------");

                notify();
            }

            // next meeting in few seconds
            try {
                Thread.sleep(5000);
            } catch (final InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + " got interrupted");
            }
        }
    }

}
