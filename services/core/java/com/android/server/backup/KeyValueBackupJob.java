package com.android.server.backup;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;

import java.util.Objects;

public class KeyValueBackupJob extends JobService {
    public static final int MAX_JOB_ID = 52418896;
    public static final int MIN_JOB_ID = 52417896;
    public static final ComponentName sKeyValueJobService =
            new ComponentName("android", KeyValueBackupJob.class.getName());
    public static final SparseBooleanArray sScheduledForUserId = new SparseBooleanArray();
    public static final SparseLongArray sNextScheduledForUserId = new SparseLongArray();

    public static void cancel(Context context, int i) {
        synchronized (KeyValueBackupJob.class) {
            ((JobScheduler) context.getSystemService("jobscheduler")).cancel(getJobIdForUserId(i));
            sScheduledForUserId.delete(i);
            sNextScheduledForUserId.delete(i);
        }
    }

    public static int getJobIdForUserId(int i) {
        int i2 = MIN_JOB_ID + i;
        if (i2 <= 52418896) {
            return i2;
        }
        throw new RuntimeException("No job IDs available in the given range");
    }

    public static boolean isScheduled(int i) {
        boolean z;
        synchronized (KeyValueBackupJob.class) {
            z = sScheduledForUserId.get(i);
        }
        return z;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void schedule(
            int r11,
            android.content.Context r12,
            long r13,
            com.android.server.backup.UserBackupManagerService r15) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.backup.KeyValueBackupJob.schedule(int,"
                    + " android.content.Context, long,"
                    + " com.android.server.backup.UserBackupManagerService):void");
    }

    public static void schedule(
            int i, Context context, UserBackupManagerService userBackupManagerService) {
        schedule(i, context, 0L, userBackupManagerService);
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        int i = jobParameters.getTransientExtras().getInt("userId");
        synchronized (KeyValueBackupJob.class) {
            sScheduledForUserId.delete(i);
            sNextScheduledForUserId.delete(i);
        }
        BackupManagerService backupManagerService = BackupManagerService.sInstance;
        Objects.requireNonNull(backupManagerService);
        try {
            backupManagerService.backupNowForUser(i);
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
