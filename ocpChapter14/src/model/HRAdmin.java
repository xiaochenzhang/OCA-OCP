package model;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class HRAdmin implements Runnable {

    private String name;

    private final CopyOnWriteArrayList<Employee> copyOnWriteEmployeeList;

    private final ReadWriteLock rwl;

    public HRAdmin(final CopyOnWriteArrayList<Employee> copyOnWriteEmployeeList, final ReadWriteLock rwl) {
        this.copyOnWriteEmployeeList = copyOnWriteEmployeeList;
        this.rwl = rwl;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public CopyOnWriteArrayList<Employee> getCopyOnWriteEmployeeList() {
        return copyOnWriteEmployeeList;
    }

    public void addEmployee() {
        final Lock lock = rwl.writeLock();
        lock.lock(); // one at a time
        try {
            final Employee employee = new Employee();
            employee.setName("Employee" + ThreadLocalRandom.current().nextInt(100));
            employee.setAge(ThreadLocalRandom.current().nextInt(18, 70));
            employee.setSalary(ThreadLocalRandom.current().nextInt(1000, 2000));

            copyOnWriteEmployeeList.addIfAbsent(employee);

            System.out.println("[" + System.currentTimeMillis() + "] " + getName() + " ("
                    + Thread.currentThread().getName() + ") is adding " + employee.getName()
                    + " to list (List size " + copyOnWriteEmployeeList.size() + ")");
        } finally {
            lock.unlock();
        }
    }

    public List<Employee> getEmployees() {
        final Lock lock = rwl.readLock();
        lock.lock(); // many at once
        try {
            final Iterator<Employee> employeeIterator = copyOnWriteEmployeeList.iterator();

            while (employeeIterator.hasNext()) {
                // ;
                System.out.println("[" + System.currentTimeMillis() + "] " + getName() + " ("
                        + Thread.currentThread().getName() + ") is reading "
                        + employeeIterator.next().getName());
            }
            return copyOnWriteEmployeeList;
        } finally {
            lock.unlock();
        }
    }

    public void modifyEmployee() {

        final Employee emp =
            getEmployees().get(ThreadLocalRandom.current().nextInt(copyOnWriteEmployeeList.size() - 1));
        final String oldName = emp.getName();

        final Lock lock = rwl.writeLock();

        // if got the lock
        if (lock.tryLock()) {
            try {
                emp.setName("NewName");
                System.out.println("[" + System.currentTimeMillis() + "] " + getName() + " ("
                        + Thread.currentThread().getName() + ") is modifying employee name from " + oldName
                        + " to " + emp.getName());
            } finally {
                lock.unlock();
            }
        }

    }

    @Override
    public void run() {
        if (copyOnWriteEmployeeList.size() <= 0) {
            addEmployee();
        } else {
            addEmployee();
            getEmployees();
            modifyEmployee();
        }
    }

}
