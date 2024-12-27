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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class KeyValueBackupJob extends JobService {
    public static final int MAX_JOB_ID = 52418896;
    public static final int MIN_JOB_ID = 52417896;
    public static final ComponentName sKeyValueJobService = new ComponentName("android", KeyValueBackupJob.class.getName());
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x0054 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0071 A[Catch: all -> 0x007e, TRY_ENTER, TryCatch #4 {all -> 0x007e, blocks: (B:4:0x0005, B:6:0x000d, B:9:0x0015, B:10:0x0017, B:30:0x0071, B:31:0x0080, B:32:0x00e7, B:49:0x00f5, B:50:0x00f6, B:12:0x0018, B:13:0x001f, B:16:0x0035, B:18:0x0039, B:21:0x004f, B:23:0x0053, B:26:0x0069, B:27:0x006a, B:37:0x00ec, B:38:0x00ed, B:41:0x00ef, B:42:0x00f0, B:45:0x00f2, B:46:0x00f3, B:15:0x0020, B:25:0x0054, B:20:0x003a), top: B:3:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void schedule(int r11, android.content.Context r12, long r13, com.android.server.backup.UserBackupManagerService r15) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.KeyValueBackupJob.schedule(int, android.content.Context, long, com.android.server.backup.UserBackupManagerService):void");
    }

    public static void schedule(int i, Context context, UserBackupManagerService userBackupManagerService) {
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
