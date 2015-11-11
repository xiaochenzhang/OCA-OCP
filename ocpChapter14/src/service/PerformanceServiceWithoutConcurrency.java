package service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import model.Employee;

public class PerformanceServiceWithoutConcurrency {

    // normal collection
    List<Employee> normalEmployeeList = new ArrayList<Employee>();

    // concurrent collection
    ConcurrentHashMap<Integer, Employee> concurrentEmployeeMap = new ConcurrentHashMap<Integer, Employee>();

    // concurrent collection
    CopyOnWriteArrayList<Employee> copyOnWriteEmployeeList = new CopyOnWriteArrayList<Employee>();

    // concurrent collection
    public void generateConcurrentEmployees() {
        final AtomicInteger id = new AtomicInteger();
        final long oldWriteTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {

            final Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setAge(ThreadLocalRandom.current().nextInt(18, 70));

            concurrentEmployeeMap.putIfAbsent(id.incrementAndGet(), employee);
        }
        System.out.println("Created " + concurrentEmployeeMap.size()
            + " employees using ConcurrentHashMap (non-parrallel) took "
                + (System.currentTimeMillis() - oldWriteTime) + " miliseconds");
    }

    // read concurrent collection
    public ConcurrentHashMap<Integer, Employee> getConcurrentEmployees() {
        final long oldReadTime = System.currentTimeMillis();

        for (final int key : concurrentEmployeeMap.keySet()) {
            concurrentEmployeeMap.get(key);
        }

        System.out.println("Getting " + concurrentEmployeeMap.size()
                + " employees using ConcurrentHashMap took " + (System.currentTimeMillis() - oldReadTime)
                + " miliseconds");
        return concurrentEmployeeMap;
    }

    // concurrent collection
    public void generateCopyOnWriteEmployees() {
        final long oldWriteTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            final Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setAge(ThreadLocalRandom.current().nextInt(18, 70));

            copyOnWriteEmployeeList.addIfAbsent(employee);
        }
        System.out.println("Created " + copyOnWriteEmployeeList.size()
            + " employees using CopyOnWriteArrayList took " + (System.currentTimeMillis() - oldWriteTime)
            + " miliseconds");

    }

    // use concurrent collection iterator
    public CopyOnWriteArrayList<Employee> getCopyOnWriteEmployees() {
        final long oldReadTime = System.currentTimeMillis();
        final Iterator<Employee> employeeIterator = copyOnWriteEmployeeList.iterator();

        while (employeeIterator.hasNext()) {
            employeeIterator.next();
        }
        System.out.println("Getting " + copyOnWriteEmployeeList.size()
            + " employees using CopyOnWriteArrayList took " + (System.currentTimeMillis() - oldReadTime)
            + " miliseconds");
        return copyOnWriteEmployeeList;
    }

    // normal collection write
    public void generateNormalEmployeeList() {
        final long oldWriteTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            final Employee employee = new Employee();
            employee.setName("Employee" + i);
            employee.setAge(ThreadLocalRandom.current().nextInt(18, 70));

            normalEmployeeList.add(employee);
        }
        System.out.println("Created " + normalEmployeeList.size() + " employees using ArrayList took "
                + (System.currentTimeMillis() - oldWriteTime) + " miliseconds");
    }

    // normal collection read
    public List<Employee> getEmployeeList() {
        final long oldReadTime = System.currentTimeMillis();
        final Iterator<Employee> employeeIterator = normalEmployeeList.iterator();

        while (employeeIterator.hasNext()) {
            employeeIterator.next();
        }
        System.out.println("Getting " + normalEmployeeList.size() + " employees using ArrayList took "
                + (System.currentTimeMillis() - oldReadTime) + " miliseconds");
        return normalEmployeeList;
    }

}
