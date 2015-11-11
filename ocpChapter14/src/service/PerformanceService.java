package service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import model.Employee;
import task.CreateBigEmployeeList;

public class PerformanceService {

    public void performanceManagement() {

        // use Fork/Join framework
        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        final CreateBigEmployeeList createBigEmployeeList = new CreateBigEmployeeList(0, 10000);

        System.out.println("Conputing performance...");

        final long oldWriteTime = System.currentTimeMillis();

        final Future<ConcurrentHashMap<Integer, Employee>> resultList =
            forkJoinPool.submit(createBigEmployeeList);
        try {
            System.out.println("Created " + resultList.get().size()
                    + " employees using ConcurrentHashMap (parallel) took "
                    + (System.currentTimeMillis() - oldWriteTime) + " miliseconds, by "
                    + forkJoinPool.getPoolSize() + " workers");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
