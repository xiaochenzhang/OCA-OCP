package logic;

import model.Chef;
import model.Customer;
import model.Fire;
import model.HamburgerBox;
import model.HamburgerResource;
import model.Manager;

public class Scheduler {

    public void schedule() {

        final HamburgerBox hamburgerBox = new HamburgerBox();
        hamburgerBox.setCount(5);

        final HamburgerResource hamburgerResource = new HamburgerResource();

        final Manager manager = new Manager();
        final Chef chef1 = new Chef(hamburgerResource, hamburgerBox, manager);
        final Chef chef2 = new Chef(hamburgerResource, hamburgerBox, manager);
        final Chef chef3 = new Chef(hamburgerResource, hamburgerBox, manager);

        final Fire fire = new Fire();
        fire.setName("!!Fire!!");

        final Customer customer = new Customer(hamburgerBox);

        final Thread chef1T = new Thread(chef1, "Chef 1");
        final Thread chef2T = new Thread(chef2, "Chef 2");
        final Thread chef3T = new Thread(chef3, "Chef 3");
        final Thread customerT = new Thread(customer, "Me");
        final Thread managerT = new Thread(manager, "Manager");

        chef1T.start();
        chef2T.start();
        chef3T.start();
        customerT.start();

        // manager starts meeting in few seconds
        try {
            Thread.sleep(4000);
            managerT.start();
        } catch (final InterruptedException ex) {
            System.out.println("Interrupted");
        }

        // fire joins
        try {
            Thread.sleep(5000);
            fire.start();
            fire.join();
        } catch (final InterruptedException ex) {
            System.out.println("Interrupted");
        }

    }

}
