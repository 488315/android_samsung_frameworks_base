package com.samsung.android.server.pm.rescueparty;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.util.ArrayMap;
import android.util.Slog;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SystemFileBackupManager {
    public static final long DEFAULT_BACKUP_PERIOD = TimeUnit.DAYS.toMillis(1);
    public static final ComponentName sFileBackupServiceName =
            new ComponentName("android", BackupJobService.class.getName());
    public static SystemFileBackupManager sInstance;
    public final ArrayMap mControllers = new ArrayMap();
    public final Object mLock = new Object();
    public boolean mSystemReady = false;
    public final AtomicBoolean mIsBackupRunning = new AtomicBoolean(false);

    public class BackupJobService extends JobService {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // android.app.job.JobService
        public final boolean onStartJob(final JobParameters jobParameters) {
            Slog.d("SystemFileBackupManager", "onStartJob: " + jobParameters.getJobId());
            new Thread(
                            new Runnable() { // from class:
                                             // com.samsung.android.server.pm.rescueparty.SystemFileBackupManager$BackupJobService$$ExternalSyntheticLambda0
                                /* JADX WARN: Multi-variable type inference failed */
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i = 0;
                                    SystemFileBackupManager.BackupJobService backupJobService =
                                            SystemFileBackupManager.BackupJobService.this;
                                    JobParameters jobParameters2 = jobParameters;
                                    int i2 = SystemFileBackupManager.BackupJobService.$r8$clinit;
                                    backupJobService.getClass();
                                    Slog.d(
                                            "SystemFileBackupManager",
                                            "Running BackupJobServiceThread");
                                    SystemFileBackupManager systemFileBackupManager =
                                            SystemFileBackupManager.getInstance();
                                    if (!systemFileBackupManager.mSystemReady) {
                                        Slog.d("SystemFileBackupManager", "System is not ready");
                                    } else if (systemFileBackupManager.mIsBackupRunning.get()) {
                                        Slog.d("SystemFileBackupManager", "Backup is running");
                                    } else {
                                        systemFileBackupManager.mIsBackupRunning.set(true);
                                        synchronized (systemFileBackupManager.mLock) {
                                            systemFileBackupManager.mControllers.forEach(
                                                    new SystemFileBackupManager$$ExternalSyntheticLambda0(
                                                            i));
                                        }
                                        systemFileBackupManager.mIsBackupRunning.set(false);
                                        i = 1;
                                    }
                                    backupJobService.jobFinished(jobParameters2, i ^ 1);
                                }
                            },
                            "BackupJobServiceThread")
                    .start();
            return true;
        }

        @Override // android.app.job.JobService
        public final boolean onStopJob(JobParameters jobParameters) {
            Slog.i("SystemFileBackupManager", "onStopJob:" + jobParameters.getJobId());
            return true;
        }
    }

    public static SystemFileBackupManager getInstance() {
        SystemFileBackupManager systemFileBackupManager;
        synchronized (SystemFileBackupManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new SystemFileBackupManager();
                }
                systemFileBackupManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return systemFileBackupManager;
    }
}
