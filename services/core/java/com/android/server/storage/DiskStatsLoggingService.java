package com.android.server.storage;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.Settings;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class DiskStatsLoggingService extends JobService {
    public static final ComponentName sDiskStatsLoggingService =
            new ComponentName("android", DiskStatsLoggingService.class.getName());

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class LogRunnable implements Runnable {
        public static final long TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(10);
        public AppCollector mCollector;
        public Context mContext;
        public File mDownloadsDirectory;
        public JobService mJobService;
        public File mOutputFile;
        public JobParameters mParams;
        public long mSystemSize;

        /* JADX WARN: Removed duplicated region for block: B:15:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0088  */
        /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x007d  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r12 = this;
                r0 = 1
                android.content.Context r1 = r12.mContext     // Catch: java.lang.IllegalStateException -> L90
                com.android.server.storage.FileCollector$MeasurementResult r1 = com.android.server.storage.FileCollector.getMeasurementResult(r1)     // Catch: java.lang.IllegalStateException -> L90
                java.io.File r2 = r12.mDownloadsDirectory
                java.io.File r2 = android.os.storage.StorageManager.maybeTranslateEmulatedPathToInternal(r2)
                com.android.server.storage.FileCollector$MeasurementResult r3 = new com.android.server.storage.FileCollector$MeasurementResult
                r3.<init>()
                com.android.server.storage.FileCollector.collectFiles(r2, r3)
                com.android.server.storage.AppCollector r2 = r12.mCollector
                long r4 = com.android.server.storage.DiskStatsLoggingService.LogRunnable.TIMEOUT_MILLIS
                monitor-enter(r2)
                java.util.concurrent.CompletableFuture r6 = r2.mStats     // Catch: java.lang.Throwable -> L2c
                r7 = 0
                if (r6 != 0) goto L2e
                java.util.concurrent.CompletableFuture r6 = new java.util.concurrent.CompletableFuture     // Catch: java.lang.Throwable -> L2c
                r6.<init>()     // Catch: java.lang.Throwable -> L2c
                r2.mStats = r6     // Catch: java.lang.Throwable -> L2c
                com.android.server.storage.AppCollector$BackgroundHandler r6 = r2.mBackgroundHandler     // Catch: java.lang.Throwable -> L2c
                r6.sendEmptyMessage(r7)     // Catch: java.lang.Throwable -> L2c
                goto L2e
            L2c:
                r12 = move-exception
                goto L8e
            L2e:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2c
                java.util.concurrent.CompletableFuture r2 = r2.mStats     // Catch: java.lang.Throwable -> L3a java.util.concurrent.TimeoutException -> L3c
                java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.Throwable -> L3a java.util.concurrent.TimeoutException -> L3c
                java.lang.Object r2 = r2.get(r4, r6)     // Catch: java.lang.Throwable -> L3a java.util.concurrent.TimeoutException -> L3c
                java.util.List r2 = (java.util.List) r2     // Catch: java.lang.Throwable -> L3a java.util.concurrent.TimeoutException -> L3c
                goto L4c
            L3a:
                r2 = move-exception
                goto L44
            L3c:
                java.lang.String r2 = "AppCollector"
                java.lang.String r4 = "AppCollector timed out"
                android.util.Log.e(r2, r4)
                goto L4b
            L44:
                java.lang.String r4 = "AppCollector"
                java.lang.String r5 = "An exception occurred while getting app storage"
                android.util.Log.e(r4, r5, r2)
            L4b:
                r2 = 0
            L4c:
                if (r2 == 0) goto L7d
                long r4 = r12.mSystemSize
                com.android.server.storage.DiskStatsFileLogger r0 = new com.android.server.storage.DiskStatsFileLogger
                r0.<init>()
                r0.mResult = r1
                long r8 = r3.imagesSize
                long r10 = r3.videosSize
                long r8 = r8 + r10
                long r10 = r3.miscSize
                long r8 = r8 + r10
                long r10 = r3.audioSize
                long r8 = r8 + r10
                r0.mDownloadsSize = r8
                r0.mSystemSize = r4
                r0.mPackageStats = r2
                java.io.File r1 = r12.mOutputFile     // Catch: java.io.IOException -> L73
                r1.createNewFile()     // Catch: java.io.IOException -> L73
                java.io.File r1 = r12.mOutputFile     // Catch: java.io.IOException -> L73
                r0.dumpToFile(r1)     // Catch: java.io.IOException -> L73
                goto L7b
            L73:
                r0 = move-exception
                java.lang.String r1 = "DiskStatsLogService"
                java.lang.String r2 = "Exception while writing opportunistic disk file cache."
                android.util.Log.e(r1, r2, r0)
            L7b:
                r0 = r7
                goto L84
            L7d:
                java.lang.String r1 = "DiskStatsLogService"
                java.lang.String r2 = "Timed out while fetching package stats."
                android.util.Log.w(r1, r2)
            L84:
                android.app.job.JobService r1 = r12.mJobService
                if (r1 == 0) goto L8d
                android.app.job.JobParameters r12 = r12.mParams
                r1.jobFinished(r12, r0)
            L8d:
                return
            L8e:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2c
                throw r12
            L90:
                r1 = move-exception
                java.lang.String r2 = "DiskStatsLogService"
                java.lang.String r3 = "Error while measuring storage"
                android.util.Log.e(r2, r3, r1)
                android.app.job.JobService r1 = r12.mJobService
                if (r1 == 0) goto La1
                android.app.job.JobParameters r12 = r12.mParams
                r1.jobFinished(r12, r0)
            La1:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.storage.DiskStatsLoggingService.LogRunnable.run():void");
        }
    }

    public static boolean isDumpsysTaskEnabled(ContentResolver contentResolver) {
        return Settings.Global.getInt(contentResolver, "enable_diskstats_logging", 1) != 0;
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        File path;
        BatteryManager batteryManager = (BatteryManager) getSystemService("batterymanager");
        if (!(batteryManager != null ? batteryManager.isCharging() : false)
                || !isDumpsysTaskEnabled(getContentResolver())) {
            jobFinished(jobParameters, true);
            return false;
        }
        VolumeInfo primaryStorageCurrentVolume =
                getPackageManager().getPrimaryStorageCurrentVolume();
        if (primaryStorageCurrentVolume == null) {
            return false;
        }
        AppCollector appCollector = new AppCollector(this, primaryStorageCurrentVolume);
        Environment.UserEnvironment userEnvironment =
                new Environment.UserEnvironment(UserHandle.myUserId());
        LogRunnable logRunnable = new LogRunnable();
        logRunnable.mDownloadsDirectory =
                userEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Map map = FileCollector.EXTENSION_MAP;
        VolumeInfo primaryStorageCurrentVolume2 =
                getPackageManager().getPrimaryStorageCurrentVolume();
        StorageManager storageManager = (StorageManager) getSystemService("storage");
        VolumeInfo findEmulatedForPrivate =
                storageManager.findEmulatedForPrivate(primaryStorageCurrentVolume2);
        long j = 0;
        if (findEmulatedForPrivate != null && (path = findEmulatedForPrivate.getPath()) != null) {
            long primaryStorageSize = storageManager.getPrimaryStorageSize() - path.getTotalSpace();
            if (primaryStorageSize > 0) {
                j = primaryStorageSize;
            }
        }
        logRunnable.mSystemSize = j;
        logRunnable.mOutputFile = new File("/data/system/diskstats_cache.json");
        logRunnable.mCollector = appCollector;
        logRunnable.mJobService = this;
        logRunnable.mParams = jobParameters;
        logRunnable.mContext = this;
        AsyncTask.execute(logRunnable);
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
