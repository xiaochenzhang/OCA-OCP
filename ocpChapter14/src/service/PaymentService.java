package service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import model.Employee;
import model.HRAdmin;
import task.PayEmployee;
import task.ScheduledPayment;

public class PaymentService {

    private final HRAdmin hrAdmin;

    // private ScheduledPayment scheduledPayment;

    public PaymentService(final HRAdmin hrAdmin) {
        this.hrAdmin = hrAdmin;

    }

    public void futurePayment() {

        final int listSize = hrAdmin.getCopyOnWriteEmployeeList().size();
        PayEmployee payEmployee;
        if (listSize > 0) {
            payEmployee =
                    new PayEmployee(hrAdmin.getCopyOnWriteEmployeeList().get(
                        ThreadLocalRandom.current().nextInt(0, listSize)), hrAdmin.getName());
        } else {
            final Employee emp = new Employee();
            emp.setName("NewEmployee");
            payEmployee = new PayEmployee(emp, hrAdmin.getName());
        }

        final ExecutorService exService = Executors.newFixedThreadPool(4);

        // pay once
        final Future<Employee> futurePayment = exService.submit(payEmployee);
        try {
            System.err.println(">>>>>>>>>>>>>>>>>>>>>> future result " + futurePayment.get().getName());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        exService.shutdown();
    }

    public void scheduledFuturePayment() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        final ScheduledPayment scheduledPayment = new ScheduledPayment(hrAdmin);

        // pay every 3 sec
        final ScheduledFuture<?> scheduledFuturePayment =
            scheduler.scheduleAtFixedRate(scheduledPayment, 1, 2, TimeUnit.SECONDS);

        // shut down in 10 seconds
        scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                scheduledFuturePayment.cancel(true);
                scheduler.shutdown();
            }
        }, 10, TimeUnit.SECONDS);

    }

}
