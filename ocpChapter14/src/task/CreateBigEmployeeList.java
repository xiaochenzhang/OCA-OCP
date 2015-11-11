package task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

import model.Employee;

public class CreateBigEmployeeList extends RecursiveTask<ConcurrentHashMap<Integer, Employee>> {

    // concurrent collection
    ConcurrentHashMap<Integer, Employee> concurrentEmployeeMap = new ConcurrentHashMap<Integer, Employee>();

    private final int start;

    private final int end;

    private static final int THRESHOLD = 100;

    // constructor
    public CreateBigEmployeeList(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return concurrent hash map of id to employee
     */
    @Override
    protected ConcurrentHashMap<Integer, Employee> compute() {
        final boolean canCompute = (end - start) <= THRESHOLD;

        // if small enough, add
        if (canCompute) {
            for (int i = start; i < end; i++) {
                final Employee employee = new Employee();
                employee.setName("Employee" + i);
                employee.setAge(ThreadLocalRandom.current().nextInt(18, 70));

                concurrentEmployeeMap.putIfAbsent(i, employee);
            }
            System.out.println("Creating employees (" + Thread.currentThread().getName()
                + "), some part is done, current list size: " + concurrentEmployeeMap.size());
            return concurrentEmployeeMap;

            // if too big, split
        } else {
            final int mid = (end - start) / 2 + start;
            final CreateBigEmployeeList leftTask = new CreateBigEmployeeList(start, mid);
            final CreateBigEmployeeList rightTask = new CreateBigEmployeeList(mid, end);
            leftTask.fork();
            // compute right
            final ConcurrentHashMap<Integer, Employee> rightResult = rightTask.compute();
            // compute left
            final ConcurrentHashMap<Integer, Employee> leftResult = leftTask.join();

            concurrentEmployeeMap.putAll(leftResult);
            concurrentEmployeeMap.putAll(rightResult);
            return concurrentEmployeeMap;
        }
    }

    public ConcurrentHashMap<Integer, Employee> getConcurrentEmployeeMap() {
        return concurrentEmployeeMap;
    }

}
