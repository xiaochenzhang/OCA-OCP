package model;

public class Chef implements Runnable {

    private final HamburgerBox hamburgerBox;

    private final HamburgerResource hamburgerResource;

    private final Manager manager;

    public Chef(final HamburgerResource hamburgerResource, final HamburgerBox hamburgerBox,
            final Manager manager) {
        this.hamburgerBox = hamburgerBox;
        this.hamburgerResource = hamburgerResource;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (manager) {
                makeHamburger();
                try {
                    // waiting for manager's meeting
                    manager.wait();
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void makeHamburger() {
        synchronized (hamburgerResource) {
            if (hamburgerResource.resourceCount > 0) {
                // decrease resource
                hamburgerResource.resourceCount--;

                hamburgerBox.setCount(hamburgerBox.getCount() + 1);
                System.out.println(Thread.currentThread().getName()
                        + " : one hamburger is done!!!!!!!!!!! TOTAL hamburger: " + hamburgerBox.getCount());
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " : no resource, cannot make anymore!!!!!!");
            }
            // make hamburger for 2 seconds
            try {
                Thread.sleep(2000);
            } catch (final InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + " got interrupted");
            }
        }
    }
}
