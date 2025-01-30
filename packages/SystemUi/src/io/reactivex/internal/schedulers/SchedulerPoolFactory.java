package io.reactivex.internal.schedulers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SchedulerPoolFactory {
    public static final boolean PURGE_ENABLED;
    public static final int PURGE_PERIOD_SECONDS;
    public static final AtomicReference PURGE_THREAD = new AtomicReference();
    public static final Map POOLS = new ConcurrentHashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PurgeProperties {
        public boolean purgeEnable;
        public int purgePeriod;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScheduledTask implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            Iterator it = new ArrayList(((ConcurrentHashMap) SchedulerPoolFactory.POOLS).keySet()).iterator();
            while (it.hasNext()) {
                ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) it.next();
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
            ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1, new RxThreadFactory("RxSchedulerPurge"));
            if (atomicReference.compareAndSet(scheduledExecutorService, newScheduledThreadPool)) {
                ScheduledTask scheduledTask = new ScheduledTask();
                long j = PURGE_PERIOD_SECONDS;
                newScheduledThreadPool.scheduleAtFixedRate(scheduledTask, j, j, TimeUnit.SECONDS);
                return;
            }
            newScheduledThreadPool.shutdownNow();
        }
    }

    private SchedulerPoolFactory() {
        throw new IllegalStateException("No instances!");
    }
}
