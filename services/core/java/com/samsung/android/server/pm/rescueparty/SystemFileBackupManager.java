package com.samsung.android.server.pm.rescueparty;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import com.samsung.android.server.pm.rescueparty.SystemFileBackupManager;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class SystemFileBackupManager {
    public static final long DEFAULT_BACKUP_PERIOD = TimeUnit.DAYS.toMillis(1);
    public static final ComponentName sFileBackupServiceName = new ComponentName("android", BackupJobService.class.getName());
    public static SystemFileBackupManager sInstance;
    public final ArrayMap mControllers = new ArrayMap();
    public final Object mLock = new Object();
    public boolean mSystemReady = false;
    public AtomicBoolean mIsBackupRunning = new AtomicBoolean(false);

    public static SystemFileBackupManager getInstance() {
        SystemFileBackupManager systemFileBackupManager;
        synchronized (SystemFileBackupManager.class) {
            if (sInstance == null) {
                sInstance = new SystemFileBackupManager();
            }
            systemFileBackupManager = sInstance;
        }
        return systemFileBackupManager;
    }

    public void onSystemReady(Context context) {
        this.mSystemReady = true;
        notifySystemReady();
        schedulePeriodicBackupJob(context);
    }

    public final void schedulePeriodicBackupJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        JobInfo.Builder builder = new JobInfo.Builder(8452948, sFileBackupServiceName);
        builder.setPeriodic(DEFAULT_BACKUP_PERIOD);
        builder.setRequiresCharging(true).setRequiresDeviceIdle(true);
        jobScheduler.schedule(builder.build());
    }

    public void scheduleOnetimeBackupJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        if (jobScheduler.getPendingJob(9543859) == null) {
            JobInfo.Builder builder = new JobInfo.Builder(9543859, sFileBackupServiceName);
            builder.setRequiresCharging(true).setRequiresDeviceIdle(true);
            jobScheduler.schedule(builder.build());
            Slog.d("SystemFileBackupManager", "Scheduled onetime backup job");
            return;
        }
        Slog.d("SystemFileBackupManager", "Already scheduled");
    }

    public final void notifySystemReady() {
        synchronized (this.mLock) {
            this.mControllers.forEach(new BiConsumer() { // from class: com.samsung.android.server.pm.rescueparty.SystemFileBackupManager$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SystemFileBackupManager.lambda$notifySystemReady$0((String) obj, (BackupController) obj2);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$notifySystemReady$0(String str, BackupController backupController) {
        Slog.d("SystemFileBackupManager", "Notifying " + str + " of system ready");
        backupController.onSystemReady();
    }

    public void registerController(BackupController backupController) {
        String controllerName = backupController.getControllerName();
        if (TextUtils.isEmpty(controllerName)) {
            return;
        }
        Slog.d("SystemFileBackupManager", "Controller " + controllerName + " is registered");
        synchronized (this.mLock) {
            this.mControllers.put(controllerName, backupController);
        }
    }

    public final boolean requestBackupFiles() {
        if (!this.mSystemReady) {
            Slog.d("SystemFileBackupManager", "System is not ready");
            return false;
        }
        if (this.mIsBackupRunning.get()) {
            Slog.d("SystemFileBackupManager", "Backup is running");
            return false;
        }
        this.mIsBackupRunning.set(true);
        synchronized (this.mLock) {
            this.mControllers.forEach(new BiConsumer() { // from class: com.samsung.android.server.pm.rescueparty.SystemFileBackupManager$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SystemFileBackupManager.lambda$requestBackupFiles$1((String) obj, (BackupController) obj2);
                }
            });
        }
        this.mIsBackupRunning.set(false);
        return true;
    }

    public static /* synthetic */ void lambda$requestBackupFiles$1(String str, BackupController backupController) {
        Slog.d("SystemFileBackupManager", "Saving files for " + backupController.getControllerName());
        backupController.saveFiles();
    }

    public class BackupJobService extends JobService {
        @Override // android.app.job.JobService
        public boolean onStartJob(final JobParameters jobParameters) {
            Slog.d("SystemFileBackupManager", "onStartJob: " + jobParameters.getJobId());
            new Thread(new Runnable() { // from class: com.samsung.android.server.pm.rescueparty.SystemFileBackupManager$BackupJobService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemFileBackupManager.BackupJobService.this.lambda$onStartJob$0(jobParameters);
                }
            }, "BackupJobServiceThread").start();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStartJob$0(JobParameters jobParameters) {
            Slog.d("SystemFileBackupManager", "Running BackupJobServiceThread");
            jobFinished(jobParameters, !SystemFileBackupManager.getInstance().requestBackupFiles());
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            Slog.i("SystemFileBackupManager", "onStopJob:" + jobParameters.getJobId());
            return true;
        }
    }
}
