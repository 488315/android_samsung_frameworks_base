package io.reactivex.internal.schedulers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class SchedulerPoolFactory {
    public static final boolean PURGE_ENABLED;
    public static final int PURGE_PERIOD_SECONDS;
    public static final AtomicReference PURGE_THREAD = new AtomicReference();
    public static final Map POOLS = new ConcurrentHashMap();

    public final class PurgeProperties {
        public boolean purgeEnable;
        public int purgePeriod;
    }

    public final class ScheduledTask implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            ArrayList arrayList = new ArrayList(((ConcurrentHashMap) SchedulerPoolFactory.POOLS).keySet());
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) obj;
                if (scheduledThreadPoolExecutor.isShutdown()) {
                    ((ConcurrentHashMap) SchedulerPoolFactory.POOLS).remove(scheduledThreadPoolExecutor);
                } else {
                    scheduledThreadPoolExecutor.purge();
                }
            }
        }
    }

    static {
        Properties properties = System.getProperties();
        PurgeProperties purgeProperties = new PurgeProperties();
        if (properties.containsKey("rx2.purge-enabled")) {
            purgeProperties.purgeEnable = Boolean.parseBoolean(properties.getProperty("rx2.purge-enabled"));
        } else {
            purgeProperties.purgeEnable = true;
        }
        if (purgeProperties.purgeEnable && properties.containsKey("rx2.purge-period-seconds")) {
            try {
                purgeProperties.purgePeriod = Integer.parseInt(properties.getProperty("rx2.purge-period-seconds"));
            } catch (NumberFormatException unused) {
                purgeProperties.purgePeriod = 1;
            }
        } else {
            purgeProperties.purgePeriod = 1;
        }
        boolean z = purgeProperties.purgeEnable;
        PURGE_ENABLED = z;
        PURGE_PERIOD_SECONDS = purgeProperties.purgePeriod;
        if (!z) {
            return;
        }
        while (true) {
            AtomicReference atomicReference = PURGE_THREAD;
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) atomicReference.get();
            if (scheduledExecutorService != null) {
                return;
            }
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, new RxThreadFactory("RxSchedulerPurge"));
            if (atomicReference.compareAndSet(scheduledExecutorService, scheduledExecutorServiceNewScheduledThreadPool)) {
                ScheduledTask scheduledTask = new ScheduledTask();
                long j = PURGE_PERIOD_SECONDS;
                scheduledExecutorServiceNewScheduledThreadPool.scheduleAtFixedRate(scheduledTask, j, j, TimeUnit.SECONDS);
                return;
            }
            scheduledExecutorServiceNewScheduledThreadPool.shutdownNow();
        }
    }

    private SchedulerPoolFactory() {
        throw new IllegalStateException("No instances!");
    }
}
