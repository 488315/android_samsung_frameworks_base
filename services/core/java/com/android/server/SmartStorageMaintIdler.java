package com.android.server;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Slog;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class SmartStorageMaintIdler extends JobService {
    public static final ComponentName SMART_STORAGE_MAINT_SERVICE = new ComponentName("android", SmartStorageMaintIdler.class.getName());
    public JobParameters mJobParams;
    public final AtomicBoolean mStarted = new AtomicBoolean(false);
    public final Runnable mFinishCallback = new Runnable() { // from class: com.android.server.SmartStorageMaintIdler.1
        @Override // java.lang.Runnable
        public void run() {
            Slog.i("SmartStorageMaintIdler", "Got smart storage maintenance service completion callback");
            if (SmartStorageMaintIdler.this.mStarted.get()) {
                SmartStorageMaintIdler smartStorageMaintIdler = SmartStorageMaintIdler.this;
                smartStorageMaintIdler.jobFinished(smartStorageMaintIdler.mJobParams, false);
                SmartStorageMaintIdler.this.mStarted.set(false);
            }
            SmartStorageMaintIdler.scheduleSmartIdlePass(SmartStorageMaintIdler.this, StorageManagerService.sSmartIdleMaintPeriod);
        }
    };

    @Override // android.app.job.JobService
    public boolean onStartJob(final JobParameters jobParameters) {
        final StorageManagerService storageManagerService = StorageManagerService.sSelf;
        if (!this.mStarted.compareAndSet(false, true)) {
            return false;
        }
        new Thread() { // from class: com.android.server.SmartStorageMaintIdler.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SmartStorageMaintIdler.this.mJobParams = jobParameters;
                StorageManagerService storageManagerService2 = storageManagerService;
                if (storageManagerService2 != null) {
                    storageManagerService2.runSmartIdleMaint(SmartStorageMaintIdler.this.mFinishCallback);
                } else {
                    SmartStorageMaintIdler.this.mStarted.set(false);
                }
            }
        }.start();
        return storageManagerService != null;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        this.mStarted.set(false);
        return false;
    }

    public static void scheduleSmartIdlePass(Context context, int i) {
        StorageManagerService storageManagerService = StorageManagerService.sSelf;
        if (storageManagerService == null || storageManagerService.isPassedLifetimeThresh()) {
            return;
        }
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        long millis = TimeUnit.MINUTES.toMillis(i);
        JobInfo.Builder builder = new JobInfo.Builder(2808, SMART_STORAGE_MAINT_SERVICE);
        builder.setMinimumLatency(millis);
        jobScheduler.schedule(builder.build());
    }
}
