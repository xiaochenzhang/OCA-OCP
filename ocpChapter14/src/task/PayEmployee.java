package task;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import model.Employee;

public class PayEmployee implements Callable<Employee> {

    private final Employee emp;

    private final String hrName;

    public PayEmployee(final Employee emp, final String hrName) {
        this.emp = emp;
        this.hrName = hrName;
    }

    @Override
    public Employee call() {
        emp.setSalary(ThreadLocalRandom.current().nextInt(1000, 2000));
        System.out.println("[" + System.currentTimeMillis() + "] " + hrName + " ("
                + Thread.currentThread().getName() + ") gave " + emp.getName() + " " + emp.getSalary()
                + " euro");
        return emp;
    }

}
