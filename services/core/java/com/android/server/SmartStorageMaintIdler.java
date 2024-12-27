package com.android.server;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.util.Slog;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SmartStorageMaintIdler extends JobService {
    public static final ComponentName SMART_STORAGE_MAINT_SERVICE =
            new ComponentName("android", SmartStorageMaintIdler.class.getName());
    public JobParameters mJobParams;
    public final AtomicBoolean mStarted = new AtomicBoolean(false);
    public final AnonymousClass1 mFinishCallback = new AnonymousClass1();

    /* renamed from: com.android.server.SmartStorageMaintIdler$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {}

        @Override // java.lang.Runnable
        public final void run() {
            Slog.i(
                    "SmartStorageMaintIdler",
                    "Got smart storage maintenance service completion callback");
            if (SmartStorageMaintIdler.this.mStarted.get()) {
                SmartStorageMaintIdler smartStorageMaintIdler = SmartStorageMaintIdler.this;
                smartStorageMaintIdler.jobFinished(smartStorageMaintIdler.mJobParams, false);
                SmartStorageMaintIdler.this.mStarted.set(false);
            }
            SmartStorageMaintIdler smartStorageMaintIdler2 = SmartStorageMaintIdler.this;
            int i = StorageManagerService.sSmartIdleMaintPeriod;
            StorageManagerService storageManagerService = StorageManagerService.sSelf;
            if (storageManagerService == null || storageManagerService.mPassedLifetimeThresh) {
                return;
            }
            JobScheduler jobScheduler =
                    (JobScheduler) smartStorageMaintIdler2.getSystemService(JobScheduler.class);
            long millis = TimeUnit.MINUTES.toMillis(i);
            JobInfo.Builder builder =
                    new JobInfo.Builder(2808, SmartStorageMaintIdler.SMART_STORAGE_MAINT_SERVICE);
            builder.setMinimumLatency(millis);
            jobScheduler.schedule(builder.build());
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        final StorageManagerService storageManagerService = StorageManagerService.sSelf;
        if (!this.mStarted.compareAndSet(false, true)) {
            return false;
        }
        new Thread() { // from class: com.android.server.SmartStorageMaintIdler.2
            /* JADX WARN: Code restructure failed: missing block: B:22:0x00b0, code lost:

               if (r0 != null) goto L33;
            */
            /* JADX WARN: Code restructure failed: missing block: B:23:0x00b2, code lost:

               r0.run();
            */
            /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:

               return;
            */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x00bb, code lost:

               if (r0 == null) goto L38;
            */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r15 = this;
                    com.android.server.SmartStorageMaintIdler r0 = com.android.server.SmartStorageMaintIdler.this
                    android.app.job.JobParameters r1 = r2
                    r0.mJobParams = r1
                    com.android.server.StorageManagerService r15 = r3
                    r1 = 0
                    if (r15 == 0) goto Lc9
                    com.android.server.SmartStorageMaintIdler$1 r0 = r0.mFinishCallback
                    java.lang.String r2 = ", min gc sleep time(ms): 0, target dirty ratio: "
                    java.lang.String r3 = ", min segment threshold: 0, dirty reclaim rate: 0.0, segment reclaim weight: 0.0, period(min): "
                    java.lang.String r4 = "Set smart idle maintenance: latest write amount: "
                    monitor-enter(r15)
                    java.lang.String r5 = "android.permission.MOUNT_FORMAT_FILESYSTEMS"
                    r15.enforcePermission$1(r5)     // Catch: java.lang.Throwable -> Lc6
                    android.os.IVold r5 = r15.mVold     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    int r5 = r5.getWriteAmount()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6 = -1
                    if (r5 != r6) goto L37
                    java.lang.String r1 = "StorageManagerService"
                    java.lang.String r2 = "Failed to get storage write record"
                    android.util.sysfwutil.Slog.w(r1, r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    if (r0 == 0) goto L2e
                    r0.run()     // Catch: java.lang.Throwable -> Lc6
                L2e:
                    monitor-exit(r15)
                    goto Lce
                L31:
                    r1 = move-exception
                    goto Lc0
                L34:
                    r1 = move-exception
                    goto Lb6
                L37:
                    r15.updateStorageWriteRecords(r5)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    boolean r6 = r15.needsCheckpoint()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    if (r6 == 0) goto L56
                    java.lang.String r6 = "android.permission.MOUNT_FORMAT_FILESYSTEMS"
                    r15.enforcePermission$1(r6)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    android.os.IVold r6 = r15.mVold     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    boolean r6 = r6.supportsBlockCheckpoint()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    if (r6 != 0) goto L4e
                    goto L56
                L4e:
                    java.lang.String r1 = "StorageManagerService"
                    java.lang.String r2 = "Skipping smart idle maintenance - block based checkpoint in progress"
                    android.util.sysfwutil.Slog.i(r1, r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    goto Lb0
                L56:
                    boolean r6 = r15.refreshLifetimeConstraint()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    if (r6 == 0) goto L73
                    boolean r6 = r15.checkChargeStatus()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    if (r6 != 0) goto L63
                    goto L73
                L63:
                    int[] r6 = r15.mStorageWriteRecords     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    java.util.stream.IntStream r6 = java.util.Arrays.stream(r6)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    int r6 = r6.sum()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    int r7 = r15.mMaxWriteRecords     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    int r6 = r6 / r7
                    r14 = r1
                    r8 = r6
                    goto L7e
                L73:
                    java.lang.String r6 = "StorageManagerService"
                    java.lang.String r7 = "Turn off gc_urgent based on checking lifetime and charge status"
                    android.util.sysfwutil.Slog.i(r6, r7)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6 = 100
                    r8 = r1
                    r14 = r6
                L7e:
                    java.lang.String r1 = "StorageManagerService"
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6.<init>(r4)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6.append(r5)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    java.lang.String r4 = ", average write amount: "
                    r6.append(r4)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6.append(r8)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6.append(r3)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    int r3 = com.android.server.StorageManagerService.sSmartIdleMaintPeriod     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6.append(r3)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6.append(r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r6.append(r14)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    java.lang.String r2 = r6.toString()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    android.util.sysfwutil.Slog.i(r1, r2)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    android.os.IVold r7 = r15.mVold     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    int r12 = com.android.server.StorageManagerService.sSmartIdleMaintPeriod     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                    r9 = 0
                    r10 = 0
                    r11 = 0
                    r13 = 0
                    r7.setGCUrgentPace(r8, r9, r10, r11, r12, r13, r14)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
                Lb0:
                    if (r0 == 0) goto Lbe
                Lb2:
                    r0.run()     // Catch: java.lang.Throwable -> Lc6
                    goto Lbe
                Lb6:
                    java.lang.String r2 = "StorageManagerService"
                    android.util.sysfwutil.Slog.wtf(r2, r1)     // Catch: java.lang.Throwable -> L31
                    if (r0 == 0) goto Lbe
                    goto Lb2
                Lbe:
                    monitor-exit(r15)
                    goto Lce
                Lc0:
                    if (r0 == 0) goto Lc5
                    r0.run()     // Catch: java.lang.Throwable -> Lc6
                Lc5:
                    throw r1     // Catch: java.lang.Throwable -> Lc6
                Lc6:
                    r0 = move-exception
                    monitor-exit(r15)
                    throw r0
                Lc9:
                    java.util.concurrent.atomic.AtomicBoolean r15 = r0.mStarted
                    r15.set(r1)
                Lce:
                    return
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.SmartStorageMaintIdler.AnonymousClass2.run():void");
            }
        }.start();
        return storageManagerService != null;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        this.mStarted.set(false);
        return false;
    }
}
