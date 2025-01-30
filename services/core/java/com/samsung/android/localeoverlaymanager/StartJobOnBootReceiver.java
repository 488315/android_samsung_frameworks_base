package com.samsung.android.localeoverlaymanager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.server.backup.BackupManagerConstants;

/* loaded from: classes2.dex */
public class StartJobOnBootReceiver extends BroadcastReceiver {
    public static final String TAG = StartJobOnBootReceiver.class.getSimpleName();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(intent.getAction())) {
            try {
                LocaleOverlayManagerWrapper.getInstance(context).initializeOverlaysForSafeMode();
            } catch (Exception e) {
                Log.e(TAG, "Error while starting LOM: " + e);
            }
            String str = TAG;
            Log.i(str, "Scheduling Job");
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            JobInfo pendingJob = jobScheduler.getPendingJob(10895);
            if (pendingJob != null) {
                Log.i(str, "Pendingjobs with ID: 10895 " + pendingJob);
                return;
            }
            Log.i(str, "No Jobs Pending with ID: 10895");
            int schedule = jobScheduler.schedule(new JobInfo.Builder(10895, new ComponentName(context, (Class<?>) LocaleOverlaysSanityService.class)).setRequiresDeviceIdle(true).setRequiresBatteryNotLow(true).setPeriodic(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS).build());
            if (schedule == 1) {
                Log.d(str, "Job scheduled");
                return;
            }
            Log.d(str, "Job scheduling failed " + schedule);
        }
    }
}
