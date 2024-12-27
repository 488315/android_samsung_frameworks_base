package com.samsung.android.localeoverlaymanager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

public class StartJobOnBootReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(intent.getAction())) {
            LocaleOverlayManagerWrapper localeOverlayManagerWrapper =
                    LocaleOverlayManagerWrapper.getInstance(context);
            try {
                Context context2 = localeOverlayManagerWrapper.mContext;
                if (context2 != null
                        && PreferenceUtils.getPreferences(context2).getBoolean("safeMode", false)) {
                    localeOverlayManagerWrapper.init(true, false);
                }
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "Error while starting LOM: ", "StartJobOnBootReceiver");
            }
            Log.i("StartJobOnBootReceiver", "Scheduling Job");
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            JobInfo pendingJob = jobScheduler.getPendingJob(10895);
            if (pendingJob != null) {
                Log.i("StartJobOnBootReceiver", "Pendingjobs with ID: 10895 " + pendingJob);
            } else {
                Log.i("StartJobOnBootReceiver", "No Jobs Pending with ID: 10895");
                int schedule =
                        jobScheduler.schedule(
                                new JobInfo.Builder(
                                                10895,
                                                new ComponentName(
                                                        context,
                                                        (Class<?>)
                                                                LocaleOverlaysSanityService.class))
                                        .setRequiresDeviceIdle(true)
                                        .setRequiresBatteryNotLow(true)
                                        .setPeriodic(
                                                BackupManagerConstants
                                                        .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
                                        .build());
                if (schedule == 1) {
                    Log.d("StartJobOnBootReceiver", "Job scheduled");
                } else {
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            schedule, "Job scheduling failed ", "StartJobOnBootReceiver");
                }
            }
        }
    }
}
