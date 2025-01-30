package com.samsung.android.localeoverlaymanager;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/* loaded from: classes2.dex */
public class LocaleOverlaysSanityService extends JobService {
    public static final String TAG = LocaleOverlaysSanityService.class.getSimpleName();

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob called: Calling ResourceOverlayService");
        try {
            LocaleOverlayManagerWrapper.getInstance(getApplicationContext()).checkSanityOfOverlays(0);
        } catch (Exception e) {
            Log.e(TAG, "Error while starting LOM: " + e);
        }
        if (jobParameters == null) {
            Log.e(TAG, "JobParameters are null");
        } else {
            Log.d(TAG, "Job execution complete: Calling jobFinished");
            jobFinished(jobParameters, true);
        }
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: Job cancelled " + jobParameters);
        return true;
    }
}
