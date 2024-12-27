package com.android.server.backup;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.util.SparseArray;

import java.util.Objects;

public class FullBackupJob extends JobService {
    public static final int MAX_JOB_ID = 52419896;
    public static final int MIN_JOB_ID = 52418896;
    public static final ComponentName sIdleService =
            new ComponentName("android", FullBackupJob.class.getName());
    public final SparseArray mParamsForUser = new SparseArray();

    public static int getJobIdForUserId(int i) {
        int i2 = 52418896 + i;
        if (i2 <= 52419896) {
            return i2;
        }
        throw new RuntimeException("No job IDs available in the given range");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void schedule(
            int r6,
            android.content.Context r7,
            long r8,
            com.android.server.backup.UserBackupManagerService r10) {
        /*
            boolean r0 = r10.isFrameworkSchedulingEnabled()
            if (r0 != 0) goto L7
            return
        L7:
            java.lang.String r0 = "jobscheduler"
            java.lang.Object r0 = r7.getSystemService(r0)
            android.app.job.JobScheduler r0 = (android.app.job.JobScheduler) r0
            android.app.job.JobInfo$Builder r1 = new android.app.job.JobInfo$Builder
            int r2 = getJobIdForUserId(r6)
            android.content.ComponentName r3 = com.android.server.backup.FullBackupJob.sIdleService
            r1.<init>(r2, r3)
            com.android.server.backup.BackupManagerConstants r10 = r10.mConstants
            monitor-enter(r10)
            java.lang.String r2 = "getFullBackupRequiredNetworkType(...) returns "
            monitor-enter(r10)     // Catch: java.lang.Throwable -> L89
            java.lang.String r3 = "BackupManagerConstants"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L8e
            int r2 = r10.mFullBackupRequiredNetworkType     // Catch: java.lang.Throwable -> L8e
            r4.append(r2)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Slog.v(r3, r2)     // Catch: java.lang.Throwable -> L8e
            int r2 = r10.mFullBackupRequiredNetworkType     // Catch: java.lang.Throwable -> L8e
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L89
            android.app.job.JobInfo$Builder r2 = r1.setRequiredNetworkType(r2)     // Catch: java.lang.Throwable -> L89
            java.lang.String r3 = "getFullBackupRequireCharging(...) returns "
            monitor-enter(r10)     // Catch: java.lang.Throwable -> L89
            java.lang.String r4 = "BackupManagerConstants"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8b
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L8b
            boolean r3 = r10.mFullBackupRequireCharging     // Catch: java.lang.Throwable -> L8b
            r5.append(r3)     // Catch: java.lang.Throwable -> L8b
            java.lang.String r3 = r5.toString()     // Catch: java.lang.Throwable -> L8b
            android.util.Slog.v(r4, r3)     // Catch: java.lang.Throwable -> L8b
            boolean r3 = r10.mFullBackupRequireCharging     // Catch: java.lang.Throwable -> L8b
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L89
            r2.setRequiresCharging(r3)     // Catch: java.lang.Throwable -> L89
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L89
            r2 = 0
            int r10 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r10 <= 0) goto L63
            r1.setMinimumLatency(r8)
        L63:
            android.content.pm.PackageManager r7 = r7.getPackageManager()
            java.lang.String r8 = "android.hardware.type.watch"
            boolean r7 = r7.hasSystemFeature(r8)
            if (r7 != 0) goto L73
            r7 = 1
            r1.setRequiresDeviceIdle(r7)
        L73:
            android.os.Bundle r7 = new android.os.Bundle
            r7.<init>()
            java.lang.String r8 = "userId"
            r7.putInt(r8, r6)
            r1.setTransientExtras(r7)
            android.app.job.JobInfo r6 = r1.build()
            r0.schedule(r6)
            return
        L89:
            r6 = move-exception
            goto L91
        L8b:
            r6 = move-exception
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L89
            throw r6     // Catch: java.lang.Throwable -> L89
        L8e:
            r6 = move-exception
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L89
            throw r6     // Catch: java.lang.Throwable -> L89
        L91:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L89
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.backup.FullBackupJob.schedule(int,"
                    + " android.content.Context, long,"
                    + " com.android.server.backup.UserBackupManagerService):void");
    }

    public final void finishBackupPass(int i) {
        synchronized (this.mParamsForUser) {
            try {
                JobParameters jobParameters = (JobParameters) this.mParamsForUser.get(i);
                if (jobParameters != null) {
                    jobFinished(jobParameters, false);
                    this.mParamsForUser.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x024a A[LOOP:0: B:39:0x00a0->B:58:0x024a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0190 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0208 A[Catch: all -> 0x01dc, TryCatch #9 {all -> 0x01dc, blocks: (B:71:0x01d4, B:74:0x0208, B:75:0x022d, B:77:0x0231, B:78:0x0246, B:130:0x0253, B:85:0x01f6, B:81:0x01e1, B:82:0x01ee), top: B:31:0x0085 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0231 A[Catch: all -> 0x01dc, TryCatch #9 {all -> 0x01dc, blocks: (B:71:0x01d4, B:74:0x0208, B:75:0x022d, B:77:0x0231, B:78:0x0246, B:130:0x0253, B:85:0x01f6, B:81:0x01e1, B:82:0x01ee), top: B:31:0x0085 }] */
    @Override // android.app.job.JobService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onStartJob(android.app.job.JobParameters r27) {
        /*
            Method dump skipped, instructions count: 612
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.FullBackupJob.onStartJob(android.app.job.JobParameters):boolean");
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        int i = jobParameters.getTransientExtras().getInt("userId");
        synchronized (this.mParamsForUser) {
            try {
                if (this.mParamsForUser.removeReturnOld(i) == null) {
                    return false;
                }
                BackupManagerService backupManagerService = BackupManagerService.sInstance;
                Objects.requireNonNull(backupManagerService);
                if (backupManagerService.isUserReadyForBackup(i)
                        && (serviceForUserIfCallerHasPermission =
                                        backupManagerService.getServiceForUserIfCallerHasPermission(
                                                i, "endFullBackup()"))
                                != null) {
                    new Thread(
                                    new UserBackupManagerService.AnonymousClass1(
                                            1, serviceForUserIfCallerHasPermission),
                                    "end-full-backup")
                            .start();
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
