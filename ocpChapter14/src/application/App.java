package application;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import model.Employee;
import model.HRAdmin;
import model.Manager;
import service.DataReadWriteService;
import service.PaymentService;
import service.PerformanceService;
import service.PerformanceServiceWithoutConcurrency;

public class App {

    public static void main(final String[] args) {

        System.err.println("App starting...\n\n");

        System.err
            .println("======================= fork/join, concurrent collections =======================");
        try {
            Thread.sleep(500);
        } catch (final InterruptedException e) {
        }

        // use fork/join
        final PerformanceService performanceService = new PerformanceService();
        performanceService.performanceManagement();
        // compare concurrent collections with normal collections
        final PerformanceServiceWithoutConcurrency pswc = new PerformanceServiceWithoutConcurrency();
        pswc.generateConcurrentEmployees();
        pswc.generateNormalEmployeeList();

        try {
            Thread.sleep(500);
        } catch (final InterruptedException e) {
        }
        System.err
        .println("======================= locks, executor(s), concurrent collections =======================");
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
        }

        // share the same object and lock
        final CopyOnWriteArrayList<Employee> copyOnWriteEmployeeList = new CopyOnWriteArrayList<Employee>();
        final ReadWriteLock rwl = new ReentrantReadWriteLock();
        final HRAdmin hrAdmin1 = new HRAdmin(copyOnWriteEmployeeList, rwl);
        final HRAdmin hrAdmin2 = new HRAdmin(copyOnWriteEmployeeList, rwl);
        final Manager manager = new Manager();
        hrAdmin1.setName("HR Admin 1");
        hrAdmin2.setName("HR Admin 2");

        // HR Admin starts managing data (with lock)
        final DataReadWriteService dataMgmt = new DataReadWriteService();
        dataMgmt.dataManagement(hrAdmin1);
        dataMgmt.dataManagement(hrAdmin2);

        // Manager starts managing data (without lock)
        dataMgmt.dataManagement(manager);

        try {
            Thread.sleep(100);
        } catch (final InterruptedException e) {
        }

        // HR admin start paying
        final PaymentService paymentService = new PaymentService(hrAdmin1);
        paymentService.futurePayment();
        paymentService.scheduledFuturePayment();
        final PaymentService paymentService2 = new PaymentService(hrAdmin2);
        paymentService2.futurePayment();
        paymentService2.scheduledFuturePayment();

    }
}
