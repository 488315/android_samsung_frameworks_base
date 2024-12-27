package com.samsung.android.localeoverlaymanager;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class LocaleOverlaysSanityService extends JobService {
    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        Log.d("LocaleOverlaysSanityService", "onStartJob called: Calling ResourceOverlayService");
        try {
            LocaleOverlayManagerWrapper.getInstance(getApplicationContext())
                    .checkSanityOfOverlays(0);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "Error while starting LOM: ", "LocaleOverlaysSanityService");
        }
        if (jobParameters == null) {
            Log.e("LocaleOverlaysSanityService", "JobParameters are null");
        } else {
            Log.d("LocaleOverlaysSanityService", "Job execution complete: Calling jobFinished");
            jobFinished(jobParameters, true);
        }
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        Log.d("LocaleOverlaysSanityService", "onStopJob: Job cancelled " + jobParameters);
        return true;
    }
}
