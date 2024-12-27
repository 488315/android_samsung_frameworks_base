package com.android.server;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.SystemProperties;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class PreloadsFileCacheExpirationJobService extends JobService {
    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        SystemProperties.set("persist.sys.preloads.file_cache_expired", "1");
        Slog.i(
                "PreloadsFileCacheExpirationJobService",
                "Set persist.sys.preloads.file_cache_expired=1");
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
