package com.android.server;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MountServiceIdler extends JobService {
    public final AnonymousClass1 mFinishCallback =
            new Runnable() { // from class: com.android.server.MountServiceIdler.1
                @Override // java.lang.Runnable
                public final void run() {
                    Slog.i("MountServiceIdler", "Got mount service completion callback");
                    synchronized (MountServiceIdler.this.mFinishCallback) {
                        try {
                            MountServiceIdler mountServiceIdler = MountServiceIdler.this;
                            if (mountServiceIdler.mStarted) {
                                mountServiceIdler.jobFinished(mountServiceIdler.mJobParams, false);
                                MountServiceIdler.this.mStarted = false;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    MountServiceIdler.scheduleIdlePass(MountServiceIdler.this);
                }
            };
    public JobParameters mJobParams;
    public boolean mStarted;
    public static final ComponentName sIdleService =
            new ComponentName("android", MountServiceIdler.class.getName());
    public static final int MOUNT_JOB_ID = 808;

    public static Calendar offsetFromTodayMidnight(int i, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(11, i2);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(5, i);
        return calendar;
    }

    public static void scheduleIdlePass(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        long timeInMillis =
                (System.currentTimeMillis() <= offsetFromTodayMidnight(0, 3).getTimeInMillis()
                                || System.currentTimeMillis()
                                        >= offsetFromTodayMidnight(0, 4).getTimeInMillis())
                        ? offsetFromTodayMidnight(1, 3).getTimeInMillis()
                                - System.currentTimeMillis()
                        : TimeUnit.SECONDS.toMillis(10L);
        JobInfo.Builder builder = new JobInfo.Builder(MOUNT_JOB_ID, sIdleService);
        builder.setRequiresDeviceIdle(true);
        builder.setRequiresCharging(true);
        builder.setRequiresBatteryNotLow(true);
        builder.setRequiresCharging(true);
        builder.setMinimumLatency(timeInMillis);
        jobScheduler.schedule(builder.build());
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        try {
            ActivityManager.getService().performIdleMaintenance();
        } catch (RemoteException unused) {
        }
        this.mJobParams = jobParameters;
        StorageManagerService storageManagerService = StorageManagerService.sSelf;
        if (storageManagerService != null) {
            synchronized (this.mFinishCallback) {
                this.mStarted = true;
            }
            storageManagerService.runIdleMaint(this.mFinishCallback);
        }
        return storageManagerService != null;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        StorageManagerService storageManagerService = StorageManagerService.sSelf;
        if (storageManagerService != null) {
            storageManagerService.abortIdleMaint(this.mFinishCallback);
            synchronized (this.mFinishCallback) {
                this.mStarted = false;
            }
        }
        return false;
    }
}
