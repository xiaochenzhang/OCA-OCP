package task;

import java.util.concurrent.ThreadLocalRandom;

import model.Employee;
import model.HRAdmin;

public class ScheduledPayment implements Runnable {

    private final HRAdmin hrAdmin;

    public ScheduledPayment(final HRAdmin hrAdmin) {
        this.hrAdmin = hrAdmin;
    }

    @Override
    public void run() {
        final int listSize = hrAdmin.getCopyOnWriteEmployeeList().size();
        final Employee emp =
            hrAdmin.getCopyOnWriteEmployeeList().get(ThreadLocalRandom.current().nextInt(0, listSize));
        emp.setSalary(ThreadLocalRandom.current().nextInt(1000, 2000));
        System.out.println("[" + System.currentTimeMillis() + "] " + hrAdmin.getName() + " ("
                + Thread.currentThread().getName() + ") gave " + emp.getName() + " " + emp.getSalary()
                + " euro (scheduled)");
    }

}
