package model;

public class Customer implements Runnable {

    private final HamburgerBox hamburgerBox;

    public Customer(final HamburgerBox hamburgerBox) {
        this.hamburgerBox = hamburgerBox;
    }

    @Override
    public void run() {
        while (true) {
            eatHamburger();
        }
    }

    private synchronized void eatHamburger() {
        if (hamburgerBox.getCount() > 0) {
            hamburgerBox.setCount(hamburgerBox.getCount() - 1);
            System.out.println(":)   :)   :)  " + Thread.currentThread().getName()
                + " : I am hungry, I am eating one hamburger! TOTAL hamburger: "
                + hamburgerBox.getCount());
        } else {
            System.out.println(":(   :(   :( " + Thread.currentThread().getName()
                + " : no hamburger for me??? ");
        }
        // eating
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException ex) {
            System.out.println(Thread.currentThread().getName() + " got interrupted");
        }
    }
}
