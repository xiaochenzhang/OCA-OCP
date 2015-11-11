package model;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Manager implements Runnable {

    private final CopyOnWriteArrayList<Employee> copyOnWriteEmployeeList =
            new CopyOnWriteArrayList<Employee>();

    // public Manager(final CopyOnWriteArrayList<Employee> copyOnWriteEmployeeList) {
    // this.copyOnWriteEmployeeList = copyOnWriteEmployeeList;
    // }

    public CopyOnWriteArrayList<Employee> getCopyOnWriteEmployeeList() {
        return copyOnWriteEmployeeList;
    }

    public void addEmployee() {
        final Employee employee = new Employee();
        employee.setName("Employee" + ThreadLocalRandom.current().nextInt(100));
        employee.setAge(ThreadLocalRandom.current().nextInt(18, 70));
        employee.setSalary(ThreadLocalRandom.current().nextInt(1000, 2000));

        copyOnWriteEmployeeList.addIfAbsent(employee);

        System.out.println("[" + System.currentTimeMillis() + "] Manager ("
                + Thread.currentThread().getName() + ") is writing " + employee.getName()
                + " to list (List-2 size " + copyOnWriteEmployeeList.size() + ")");
    }

    public List<Employee> getEmployees() {
        // using iterator
        final Iterator<Employee> employeeIterator = copyOnWriteEmployeeList.iterator();

        while (employeeIterator.hasNext()) {
            System.out.println("[" + System.currentTimeMillis() + "] Manager ("
                    + Thread.currentThread().getName() + ") is reading " + employeeIterator.next().getName());
        }
        return copyOnWriteEmployeeList;
    }

    public void modifyEmployee() {

        final Employee emp =
                getEmployees().get(ThreadLocalRandom.current().nextInt(copyOnWriteEmployeeList.size() - 1));
        final String oldName = emp.getName();

        emp.setName("NewName");
        System.out.println("[" + System.currentTimeMillis() + "] Manager ("
                + Thread.currentThread().getName() + ") is modifying employee name from " + oldName + " to "
                + emp.getName());
    }

    public void removeEmployee() {
        final Employee emp =
                getEmployees().get(ThreadLocalRandom.current().nextInt(copyOnWriteEmployeeList.size() - 1));
        final String oldName = emp.getName();
        copyOnWriteEmployeeList.remove(emp);
        System.out.println("[" + System.currentTimeMillis() + "] Manager ("
                + Thread.currentThread().getName() + ") is removing employee: " + oldName + "(List-2 size "
                + copyOnWriteEmployeeList.size() + ")");
    }

    @Override
    public void run() {
        if (copyOnWriteEmployeeList.size() <= 0) {
            addEmployee();
        } else {
            addEmployee();
            getEmployees();
            modifyEmployee();
            removeEmployee();
        }
    }

}
