package service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import model.HRAdmin;
import model.Manager;

public class DataReadWriteService {

    public void dataManagement(final HRAdmin hrAdmin) {

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

        // read/write every sec
        final ScheduledFuture<?> scheduledDataMgmt =
            scheduler.scheduleAtFixedRate(hrAdmin, 0, 2, TimeUnit.SECONDS);

        // shut down in 10 seconds
        scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                scheduledDataMgmt.cancel(true);
                scheduler.shutdown();
            }
        }, 15, TimeUnit.SECONDS);
    }

    public void dataManagement(final Manager manager) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

        // read/write every sec
        final ScheduledFuture<?> scheduledDataMgmt =
            scheduler.scheduleAtFixedRate(manager, 0, 2, TimeUnit.SECONDS);

        // shut down in 10 seconds
        scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                scheduledDataMgmt.cancel(true);
                scheduler.shutdown();
            }
        }, 10, TimeUnit.SECONDS);
    }

}
