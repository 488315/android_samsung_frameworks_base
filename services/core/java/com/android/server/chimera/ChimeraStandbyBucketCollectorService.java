package com.android.server.chimera;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import com.android.server.backup.BackupManagerConstants;

/* loaded from: classes.dex */
public abstract class ChimeraStandbyBucketCollectorService extends JobService {
    public static ComponentName sSBCollectorService = new ComponentName("android", ChimeraStandbyBucketCollectorService.class.getName());

    public static void schedule(Context context) {
        try {
            ((JobScheduler) context.getSystemService("jobscheduler")).schedule(new JobInfo.Builder(9900, sSBCollectorService).setPeriodic(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, 5184000L).setRequiresDeviceIdle(true).setPersisted(true).build());
            Log.d("ChimeraStandbyBucketCollectorService", "Collect StandbyBucket job...");
        } catch (Exception e) {
            Log.d("ChimeraStandbyBucketCollectorService", "Collect StandbyBucket has exception : " + e.getMessage());
        }
    }
}
